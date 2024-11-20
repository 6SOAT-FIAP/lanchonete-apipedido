variable "aws_region" {
  description = "Região da AWS"
  type        = string
  default     = "us-east-1"
}

variable "subnet_ids" {
  description = "Lista de IDs das subnets"
  type        = list(string)
  default     = ["subnet-0e30ba8519b6badbc", "subnet-01a5ce07590a99018"]
}

variable "vpc_id" {
  description = "VPC ID"
  type        = string
  default     = "vpc-060f9c0343485cb7b"
}

# Database configuration
variable "db_username" {
  description = "The username for the RDS instance"
  type        = string
  sensitive   = true
  default     = "root"
}

variable "db_password" {
  description = "The password for the RDS instance"
  type        = string
  sensitive   = true
  default     = "rootPass123456"
}

variable "db_name" {
  description = "Database name"
  type        = string
  default     = "lanchonete_produto_db"
}

variable "db_identifier" {
  description = "The identifier for the RDS instance"
  type        = string
  default     = "lanchonete-produto-db"
}