resource "aws_ecs_task_definition" "task_apipedido" {
  family                = "TSK-${var.projectName}"
  container_definitions = jsonencode([
    {
      name        = var.projectName
      essential   = true,
      image       = "${aws_ecr_repository.repository_apipedido.repository_url}:latest",
      environment = [
        {
          name  = "SPRING_PROFILES_ACTIVE"
          value = "production"
        },
        {
          name  = "SPRING_DATASOURCE_URL"
          value = "jdbc:mysql://${data.aws_db_instance.database.endpoint}/dbbluesburger?useSSL=false&useTimezone=true&serverTimezone=UTC"
        },
        {
          name  = "SPRING_DATASOURCE_USERNAME"
          value = var.rdsUser
        },
        {
          name  = "SPRING_DATASOURCE_PASSWORD"
          value = var.rdsPass
        },
        {
          name  = "SPRING_JPA_GENERATE_DDL"
          value = "true"
        },
        {
          name  = "SPRING_JPA_HIBERNATE_DDL_AUTO"
          value = "update"
        },
        {
          name  = "AWS_ACCOUNT_ID",
          value = data.aws_caller_identity.current.account_id
        },
        {
          name  = "AWS_ACCESS_KEY_ID"
          value = var.aws_access_key
        },
        {
          name  = "AWS_SECRET_ACCESS_KEY"
          value = var.aws_secret_key
        }
      ]
      logConfiguration = {
        logDriver = "awslogs"
        options   = {
          awslogs-group         = aws_cloudwatch_log_group.cloudwatch-log-group.name
          awslogs-region        = var.aws_region
          awslogs-stream-prefix = "ecs"
        }
      }
      portMappings = [
        {
          containerPort = 8080
          hostPort      = 8080
          protocol      = "tcp"
        }
      ]
    }
  ])
  network_mode = "awsvpc"

  requires_compatibilities = ["FARGATE"]

  execution_role_arn = "arn:aws:iam::${data.aws_caller_identity.current.account_id}:role/OrderingSystemServiceRoleForECS"

  memory = "4096"
  cpu    = "2048"

}
