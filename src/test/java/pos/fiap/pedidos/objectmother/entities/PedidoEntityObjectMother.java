package pos.fiap.pedidos.objectmother.entities;

import pos.fiap.pedidos.adapter.out.database.entities.PedidoEntity;
import pos.fiap.pedidos.domain.enums.StatusPedidoEnum;

import java.util.List;

import static pos.fiap.pedidos.objectmother.entities.ProdutoEntityObjectMother.getProdutoEntityMock;

public class PedidoEntityObjectMother {

    public static PedidoEntity getPedidoEntityMock() {
        return PedidoEntity.builder()
                .numeroPedido("12342")
                .cpfCliente("12345678")
                .valorTotal(Double.valueOf("100.00"))
                .itens(List.of(getProdutoEntityMock()))
                .statusPedido(StatusPedidoEnum.RECEBIDO)
                .build();
    }

    public static PedidoEntity getPedidoEntityStatusFinalizadoMock() {
        return PedidoEntity.builder()
                .numeroPedido("12342")
                .cpfCliente("12345678")
                .valorTotal(Double.valueOf("100.00"))
                .itens(List.of(getProdutoEntityMock()))
                .statusPedido(StatusPedidoEnum.FINALIZADO)
                .build();
    }

    public static PedidoEntity getPedidoEntityStatusNullMock() {
        return PedidoEntity.builder()
                .numeroPedido("12342")
                .cpfCliente("12345678")
                .valorTotal(Double.valueOf("100.00"))
                .itens(List.of(getProdutoEntityMock()))
                .build();
    }
}
