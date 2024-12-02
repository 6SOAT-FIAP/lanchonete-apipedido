resource "aws_cloudwatch_log_group" "ecs_log_group" {
  name              = "/ecs/api-pedido"
  retention_in_days = 7
}

resource "aws_ecs_task_definition" "api_task" {
  family                   = "api-pedido-task"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = "256"
  memory                   = "512"

  container_definitions = jsonencode([{
    name      = "api-container-pedido"
    image     = "733005211464.dkr.ecr.us-east-1.amazonaws.com/lanchonete-apipedido:latest"
    portMappings = [
      {
        containerPort = 8082
        protocol      = "tcp"
      }
    ]
    essential = true
    logConfiguration = {
      logDriver = "awslogs"
      options = {
        awslogs-group         = "/ecs/api-pedido"
        awslogs-region        = "us-east-1"
        awslogs-stream-prefix = "ecs"
      }
    }
  }])

  execution_role_arn = "arn:aws:iam::733005211464:role/LabRole"
}

resource "aws_security_group" "ecs_service_sg" {
  name   = "ecs-pedido-service-sg"
  vpc_id = var.vpc_id

  ingress {
    from_port   = 8082
    to_port     = 8082
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

resource "aws_lb_target_group" "api_target_group" {
  name     = "api-pedido-target-group"
  port     = 8082
  protocol = "HTTP"
  vpc_id   = var.vpc_id
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

resource "aws_lb_listener_rule" "pedido_rule" {
  listener_arn = var.api_listener_arn
  priority     = 30

  condition {
    path_pattern {
      values = ["/api/v1/pedido*"]
    }
  }

  action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.api_target_group.arn
  }
}

resource "aws_ecs_service" "api_service" {
  name            = "api-pedido-service"
  cluster         = var.ecs_cluster_arn
  task_definition = aws_ecs_task_definition.api_task.arn
  launch_type     = "FARGATE"
  desired_count   = 1

  network_configuration {
    subnets          = var.subnet_ids
    security_groups  = [aws_security_group.ecs_service_sg.id]
    assign_public_ip = true
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.api_target_group.arn
    container_name   = "api-container-pedido"
    container_port   = 8082
  }
}

resource "aws_db_subnet_group" "lanchonete_db_subnet_group" {
  name       = "lanchonete-db-pedido-subnet-group"
  subnet_ids = var.subnet_ids
}

resource "aws_security_group" "lanchonete_db_sg" {
  name        = "lanchonete_db_pedido_sg"
  description = "Acesso ao RDS PostgreSQL"
  vpc_id      = var.vpc_id

  ingress {
    from_port       = 3306
    to_port         = 3306
    protocol        = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_db_instance" "lanchonete_db_pedido" {
  db_name              = "lanchonete_db_pedido"
  identifier           = "lanchonete-db-pedido"
  allocated_storage    = 20
  engine               = "mysql"
  engine_version       = "8.0.39"
  instance_class       = "db.t3.micro"
  username             = var.db_username
  password             = var.db_password
  skip_final_snapshot  = true

  vpc_security_group_ids = [aws_security_group.lanchonete_db_sg.id]
  db_subnet_group_name   = aws_db_subnet_group.lanchonete_db_subnet_group.name
}