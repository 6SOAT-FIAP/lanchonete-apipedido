Feature: Checkout do pedido

  Scenario: Realizar o checkout de um pedido
    Given o seguinte payload para realizar o checkout do pedido:
      """
      {
        "cpfCliente": "1425",
        "itens": [
          {
            "id": "1"
          }
        ]
      }
      """
    When envio uma requisição POST para "/api/v1/pedido" com os dados do pedido
    Then o código de resposta deve ser 201 para realização do pedido
    And o corpo da resposta do pedido deve conter todos os campos não nulos
