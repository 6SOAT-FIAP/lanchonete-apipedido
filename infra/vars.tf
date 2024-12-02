variable "aws_region" {
  description = "Região da AWS"
  type        = string
  default     = "us-east-1"
}

variable "subnet_ids" {
  description = "Lista de IDs das subnets"
  type        = list(string)
}

variable "vpc_id" {
  description = "VPC ID"
  type        = string
}

variable "ecs_cluster_arn" {
  description = "ID do cluster ECS"
  type        = string
}

variable "load_balancer_arn" {
  description = "ARN do Load Balancer"
  type        = string
}

variable "api_listener_arn" {
  description = "O ARN do listener do Load Balancer"
  type        = string
}

variable "db_username" {
  description = "Username RDS"
  type        = string
  sensitive   = true
  default     = "root"
}

variable "db_password" {
  description = "Password RDS"
  type        = string
  sensitive   = true
  default     = "rootPass123456"
}