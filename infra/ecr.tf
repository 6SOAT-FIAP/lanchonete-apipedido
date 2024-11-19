resource "aws_ecr_repository" "repository_apipedido" {
  name                 = "lanchonete-apipedido"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = false
  }

  force_delete = true
  depends_on = [null_resource.push_image_apipedido_to_ecr]
}

resource "aws_ecr_repository_policy" "repository-apipedido-policy" {
  repository = aws_ecr_repository.repository_apipedido.name

  policy = <<EOF
  {
     "Version": "2008-10-17",
     "Statement": [
         {
             "Sid": "new policy",
             "Effect": "Allow",
             "Principal": {
                 "AWS": "arn:aws:iam::${data.aws_caller_identity.current.account_id}:root"
             },
             "Action": [
                 "ecr:GetDownloadUrlForLayer",
                 "ecr:BatchGetImage",
                 "ecr:BatchCheckLayerAvailability",
                 "ecr:PutImage",
                 "ecr:InitiateLayerUpload",
                 "ecr:UploadLayerPart",
                 "ecr:CompleteLayerUpload",
                 "ecr:DescribeRepositories",
                 "ecr:GetRepositoryPolicy",
                 "ecr:ListImages",
                 "ecr:DeleteRepository",
                 "ecr:BatchDeleteImage",
                 "ecr:SetRepositoryPolicy",
                 "ecr:DeleteRepositoryPolicy"
             ]
         }
     ]
 }
 EOF
}

resource "aws_ecr_lifecycle_policy" "repository-pedido-lifecycle" {
  repository = aws_ecr_repository.repository_apipedido.name

  policy = <<EOF
 {
     "rules": [
         {
             "rulePriority": 1,
             "description": "Expire images count more than 2",
             "selection": {
                 "tagStatus": "any",
                 "countType": "imageCountMoreThan",
                 "countNumber": 2
             },
             "action": {
                 "type": "expire"
             }
         }
     ]
 }
 EOF
}

# Definição de um recurso de execução local para fazer o push da imagem "apipedido"
resource "null_resource" "push_image_apipedido_to_ecr" {
  provisioner "local-exec" {
    command     = <<-EOT
      aws ecr get-login-password --region ${var.aws_region} | docker login --username AWS --password-stdin ${aws_ecr_repository.repository_apipedido.repository_url}
      mkdir -p ./temp_repo_apipedido  # Cria diretório temporário exclusivo para o pedido
      cd ./temp_repo_apipedido || exit 1
      git clone https://github.com/6SOAT-FIAP/lanchonete-apipedido ./lanchonete-apipedido-repo
      cd ./lanchonete-apipedido-repo || exit 1
      docker build -t ${aws_ecr_repository.repository_apipedido.repository_url}:latest .
      docker push ${aws_ecr_repository.repository_apipedido.repository_url}:latest
      rm -rf ./temp_repo_order
    EOT
    working_dir = path.module
  }
  depends_on = [aws_ecr_repository.repository_apipedido]
}