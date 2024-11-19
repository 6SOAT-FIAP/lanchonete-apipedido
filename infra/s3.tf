terraform {
  backend "s3" {
    bucket = "lanchonete-bucket"
    key    = "pedidoapi/terraform.tfstate"
    region = var.aws_region
  }
}