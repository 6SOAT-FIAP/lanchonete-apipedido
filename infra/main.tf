resource "aws_cloudwatch_log_group" "ecs_log_group" {
  name              = "/ecs/api-container"
  retention_in_days = 7
}

resource "aws_ecs_cluster" "api_cluster" {
  name = "api-cluster"
}

resource "aws_ecs_task_definition" "api_task" {
  family                   = "api-task"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = "256"
  memory                   = "512"

  container_definitions = jsonencode([
    {
      name         = "api-container"
      image        = "767397955655.dkr.ecr.us-east-1.amazonaws.com/lanchonete-apipedido:latest"
      portMappings = [
        {
          containerPort = 8080
          protocol      = "tcp"
        }
      ]
      essential        = true
      logConfiguration = {
        logDriver = "awslogs"
        options   = {
          awslogs-group         = "/ecs/api-container"
          awslogs-region        = "us-east-1"
          awslogs-stream-prefix = "ecs"
        }
      }
    }
  ])

  execution_role_arn = "arn:aws:iam::767397955655:role/LabRole"
}

resource "aws_security_group" "ecs_service_sg" {
  name   = "ecs-service-sg"
  vpc_id = var.vpc_id

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_security_group" "alb_sg" {
  name   = "alb-sg"
  vpc_id = var.vpc_id

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_lb" "api_alb" {
  name                       = "api-alb"
  internal                   = false
  load_balancer_type         = "application"
  security_groups            = [aws_security_group.alb_sg.id]
  subnets                    = var.subnet_ids
  enable_deletion_protection = false

  enable_cross_zone_load_balancing = true
}

resource "aws_lb" "nlb" {
  name               = "api-nlb"
  internal           = false
  load_balancer_type = "network"
  subnets            = var.subnet_ids
}

resource "aws_lb_target_group" "api_target_group" {
  name        = "api-target-group"
  port        = 8080
  protocol    = "HTTP"
  vpc_id      = var.vpc_id
  target_type = "ip"

  health_check {
    path                = "/health"
    interval            = 60
    timeout             = 10
    healthy_threshold   = 3
    unhealthy_threshold = 3
    matcher             = "200"
  }
}

resource "aws_lb_listener" "api_listener" {
  load_balancer_arn = aws_lb.api_alb.arn
  port              = "80"
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.api_target_group.arn
  }
}

resource "aws_ecs_service" "api_service" {
  name                               = "api-service"
  cluster                            = aws_ecs_cluster.api_cluster.id
  task_definition                    = aws_ecs_task_definition.api_task.arn
  launch_type                        = "FARGATE"
  scheduling_strategy                = "REPLICA"
  desired_count                      = 1
  deployment_minimum_healthy_percent = 50
  deployment_maximum_percent         = 200
  wait_for_steady_state              = true
  force_new_deployment               = true
  lifecycle {
    ignore_changes = [desired_count]
  }

  network_configuration {
    subnets          = var.subnet_ids
    security_groups  = [aws_security_group.ecs_service_sg.id]
    assign_public_ip = true
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.api_target_group.arn
    container_name   = "api-container"
    container_port   = 8080
  }
}

resource "aws_security_group" "lanchonete_pedido_db_sg" {
  name        = "lanchonete-pedido-db-sg"
  description = "Allow traffic to RDS instance"
  vpc_id      = var.vpc_id

  ingress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# Database
resource "aws_db_instance" "lanchonete_pedido_database" {
  allocated_storage    = 10
  engine               = "mysql"
  engine_version       = "8.0.35"
  instance_class       = "db.t3.micro"
  identifier           = var.db_identifier
  username             = var.db_username
  password             = var.db_password
  db_name              = var.db_name
  parameter_group_name = "default.mysql8.0"
  skip_final_snapshot  = true
  publicly_accessible  = true

  vpc_security_group_ids = [aws_security_group.lanchonete_pedido_db_sg.id]
}

#API GATEWAY
resource "aws_api_gateway_rest_api" "rest_api" {
  name = "${var.project_name}-api-gateway-rest-api"
  endpoint_configuration {
    types = ["REGIONAL"]
    # vpc_endpoint_ids = [data.aws_vpc_endpoint.vpc_endpoint.id]
  }
  body = jsonencode({
    openapi = "3.0.1"
    info    = {
      title   = "Pedidos"
      version = "1.0"
    }
    paths = {
      "/pedido" = {
        get = {
          x-amazon-apigateway-integration = {
            httpMethod           = "GET"
            payloadFormatVersion = "1.0"
            type                 = "HTTP_PROXY"
            uri                  = "http://${aws_lb.api_alb.dns_name}/api/v1/pedido"
          }
        },
        post = {
          x-amazon-apigateway-integration = {
            httpMethod           = "POST"
            payloadFormatVersion = "1.0"
            type                 = "HTTP_PROXY"
            uri                  = "http://${aws_lb.api_alb.dns_name}/api/v1/pedido"
          }
        }
      }
    }
  })
}

resource "aws_api_gateway_deployment" "rest_api" {
  rest_api_id = aws_api_gateway_rest_api.rest_api.id

  triggers = {
    redeployment = sha1(jsonencode(aws_api_gateway_rest_api.rest_api.body))
  }

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_api_gateway_stage" "rest_api" {
  deployment_id = aws_api_gateway_deployment.rest_api.id
  rest_api_id   = aws_api_gateway_rest_api.rest_api.id
  stage_name    = "prod"
}

resource "aws_api_gateway_method_settings" "rest_api" {
  rest_api_id = aws_api_gateway_rest_api.rest_api.id
  stage_name  = aws_api_gateway_stage.rest_api.stage_name
  method_path = "*/*"

  settings {
    metrics_enabled = false
  }
}

resource "aws_api_gateway_vpc_link" "vpc_link" {
  name        = "${var.project_name}-api-gateway-vpc-link"
  target_arns = [aws_lb.nlb.arn]
}