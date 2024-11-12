package pos.fiap.pedidos.objectmother.model;

import pos.fiap.pedidos.domain.model.DadosPedido;

import java.util.List;

import static pos.fiap.pedidos.objectmother.model.DadosProdutoObjectMother.getDadosProdutoMock;

public class DadosPedidoObjectMother {

    public static DadosPedido getDadosPedidoMock() {
        return DadosPedido.builder()
                .cpfCliente("02315132")
                .numeroPedido("121651521")
                .itens(List.of(getDadosProdutoMock()))
                .valorTotal(Double.valueOf("100.00"))
                .mensagemPedido("")
                .build();
    }
}
