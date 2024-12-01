package pos.fiap.pedidos.objectmother.entities;

import pos.fiap.pedidos.adapter.out.database.entities.PedidoEntity;
import pos.fiap.pedidos.domain.enums.StatusPedidoEnum;

import java.util.List;


public class PedidoEntityObjectMother {

    public static PedidoEntity getPedidoEntityMock() {
        return PedidoEntity.builder()
                .numeroPedido("12342")
                .cpfCliente("12345678")
                .valorTotal(Double.valueOf("100.00"))
                .itens(List.of("123124"))
                .statusPedido(StatusPedidoEnum.RECEBIDO)
                .build();
    }

    public static PedidoEntity getPedidoEntityStatusFinalizadoMock() {
        return PedidoEntity.builder()
                .numeroPedido("12342")
                .cpfCliente("12345678")
                .valorTotal(Double.valueOf("100.00"))
                .itens(List.of("123124"))
                .statusPedido(StatusPedidoEnum.FINALIZADO)
                .build();
    }

    public static PedidoEntity getPedidoEntityStatusNullMock() {
        return PedidoEntity.builder()
                .numeroPedido("12342")
                .cpfCliente("12345678")
                .valorTotal(Double.valueOf("100.00"))
                .itens(List.of("123124"))
                .build();
    }
}
