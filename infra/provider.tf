terraform {

  required_providers {

    docker = {
      source  = "kreuzwerker/docker"
      version = ">= 2.13.0"
    }

    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }

  backend "s3" {
    bucket = "lanchonete-bucket"
    key    = "lanchonete-bucket/infra.tfstate"
    region = var.aws_region
  }
}

provider "aws" {
  region = var.aws_region
  default_tags {
    tags = var.tags
  }
}

provider "docker" {
  registry_auth {
    address  = local.aws_ecr_url
    username = data.aws_ecr_authorization_token.token.username
    password = data.aws_ecr_authorization_token.token.password
  }
}