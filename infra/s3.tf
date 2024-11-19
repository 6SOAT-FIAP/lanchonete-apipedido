terraform {
  backend "s3" {
    bucket = "lanchoneteapi-pedido-bucket"
    key    = "api/terraform.tfstate"
    region = "sa-east-1"
  }
}