output "ecs-service-name-apipedido" {
  value = aws_ecs_service.service_apipedido.name
}

output "alb_dns_name" {
  value = aws_lb.alb.dns_name
}

output "rest_api_id" {
  value = aws_api_gateway_rest_api.rest_api.id
}