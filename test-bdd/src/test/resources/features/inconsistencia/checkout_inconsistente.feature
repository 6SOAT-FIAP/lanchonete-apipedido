Feature: Checkout do pedido inconsistente

  Scenario: Tentar realizar o checkout de um pedido inconsistente
    Given o seguinte payload inconsistente para realizar o checkout do pedido:
      """
      {
        "cpfCliente": "",
        "itens": ["lanche": "x-tudo"]
      }
      """
    When envio uma requisição POST para "/api/v1/pedido" com os dados do pedido inconsistente
    Then o código de resposta deve ser 400 para erro no pedido
