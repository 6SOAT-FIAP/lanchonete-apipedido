terraform {
  backend "s3" {
    bucket = "lanchonete-cezar-bucket"
    key    = "lanchonete-ecs-pedido/terraform.tfstate"
    region = "us-east-1"
  }
}