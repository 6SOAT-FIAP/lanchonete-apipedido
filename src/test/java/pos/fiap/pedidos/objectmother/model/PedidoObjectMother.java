package pos.fiap.pedidos.objectmother.model;

import pos.fiap.pedidos.domain.model.entity.Pedido;

import java.util.List;

import static pos.fiap.pedidos.objectmother.model.ProdutoObjectMother.getProdutoMock;

public class PedidoObjectMother {

    public static Pedido getPedidoMock() {
        return Pedido.builder()
                .numeroPedido("456486")
                .mensagemPedido("")
                .cpfCliente("12345498")
                .valorTotal(Double.valueOf("100.00"))
                .itens(List.of(getProdutoMock()))
                .build();
    }
}
