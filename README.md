# API de Pedidos

## O que é o projeto

Há uma lanchonete de bairro que está se expandindo devido ao seu grande sucesso.

Nesse contexto, um sistema de controle de pedidos é essencial para garantir que a lanchonete possa atender os clientes
de maneira eficiente, gerenciando seus pedidos e estoques de forma adequada.

Para solucionar o problema, o projeto visa oferecer à lanchonete um sistema de autoatendimento de fast food que permite
aos clientes selecionar e fazer pedidos sem precisar interagir com um atendente.

## Objetivos

Este serviço inclui as seguintes funcionalidades:

- Check-out dos pedidos;
- Listar pedidos;
- Atualizar status dos pedidos.

## Desenho da arquitetura

Abaixo ilustra-se o desenho da arquitetura:

<p align = "center">
  <img src = assets/arquitetura/arquitetura_servicos.svg>
</p>

## 💻 Pré-requisitos

Antes de começar, verifique se você atendeu aos seguintes requisitos:

* Você tem uma máquina `<Windows / Linux / Mac>`.
* Docker
* Postman
* Jdk 17 ou superior

## Como iniciar o projeto localmente

Execute o docker compose para subir o banco de dados:

```bash
docker-compose up -d
```

## Como executar o Cucumber localmente

1. Execute o docker compose;
2. Suba o serviço em um console;
3. Em outro execute o CucumberRunnerTest.

## Collection

Acesse a [**collection**](assets/collection/API_PEDIDO.postman_collection) do Postman com todas as APIs desenvolvidas.

## Guia instrutivo

Acesse o [**guia**](assets/collection/guia.md) para execução das APIs.