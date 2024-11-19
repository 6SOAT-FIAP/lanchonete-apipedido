variable "projectName" {
  default = "lanchonete-apipedido"
}

variable "clusterName" {
  default = "lanchonete-apipedido-cluster"
}

variable "vpcCIDR" {
  default = "172.31.0.0/20"
}

variable "rdsUser" {
  description = "Inserir usuario do banco em secrets"
  type        = string
  sensitive   = true
}

variable "rdsPass" {
  description = "Inserir senha do banco em secrets"
  type        = string
  sensitive   = true
}

variable "project_name_dynamo" {
  description = "Nome do projeto. Por exemplo, 'bluesburguer'."
  default     = "payment"
  type        = string
}

variable "tags" {
  type = map(string)
  default = {
    App      = "lanchonete-apipedido",
    Ambiente = "Desenvolvimento"
  }
}

variable "aws_region" {
  default = "us-east-1"
}

variable "aws_access_key" {
  default = "sua_access_key"
}

variable "aws_secret_key" {
  default = "sua_secret_key"
}

variable "domain_name" {
  description = "Inserir aws secret key"
  type        = string
  default     = "lanchonete-apipedido.terraform.com"
}

variable "github_repo_url" {
  default = "https://github.com/6SOAT-FIAP/lanchonete-apipedido"
}


locals {
  tags = {
    created_by = "terraform"
  }

  aws_ecr_url = "${data.aws_caller_identity.current.account_id}.dkr.ecr.${var.aws_region}.amazonaws.com"
}