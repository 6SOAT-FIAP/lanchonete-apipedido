# Guia para execução

Segue abaixo instruções para execução.

## - Cadastrar pedido

O próximo passo é cadastrar o pedido do cliente:

[POST] */api/v1/pedido*

```json
{
  "cpfCliente": "1001",
  "itens": [
    {
      "id": "1000"
    }
  ]
}
```

## - Listar pedidos

É possível listar os pedidos:

[GET] */api/v1/pedido*

## - Atualizar pedido

Com o andamento do processo, deve-se atualizar o status do pedido:

[PATCH] */api/v1/pedido/{idPedido}*

```json
{
  "statusPedido": "PRONTO"
}
```