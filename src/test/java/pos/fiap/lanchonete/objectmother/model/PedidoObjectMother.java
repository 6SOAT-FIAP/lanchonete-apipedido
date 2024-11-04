package pos.fiap.lanchonete.objectmother.model;

import pos.fiap.lanchonete.domain.model.entity.Pedido;

import java.util.List;

import static pos.fiap.lanchonete.objectmother.model.ProdutoObjectMother.getProdutoMock;

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
