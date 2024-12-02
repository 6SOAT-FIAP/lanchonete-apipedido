package pos.fiap.pedidos.objectmother;

import pos.fiap.pedidos.adapter.in.api.dto.PedidoRequestDto;
import pos.fiap.pedidos.adapter.in.api.dto.PedidoResponseDto;
import pos.fiap.pedidos.adapter.out.database.entities.PedidoEntity;
import pos.fiap.pedidos.domain.enums.StatusPedidoEnum;
import pos.fiap.pedidos.domain.model.entity.Pedido;

import java.util.List;

import static pos.fiap.pedidos.domain.enums.CategoriaEnum.ACOMPANHAMENTO;
import static pos.fiap.pedidos.objectmother.ProdutoObjectMother.getProdutoMock;

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

    public static PedidoRequestDto getPedidoRequestDtoMock() {
        return PedidoRequestDto.builder()
                .cpfCliente("12345648")
                .itens(List.of(getWrapperProdutoRequestDtoMock()))
                .build();
    }

    private static PedidoRequestDto.WrapperProdutoRequestDto getWrapperProdutoRequestDtoMock() {
        return PedidoRequestDto.WrapperProdutoRequestDto.builder()
                .id("13541")
                .build();
    }

    public static PedidoResponseDto getPedidoResponseDtoMock() {
        return PedidoResponseDto.builder()
                .cpfCliente("56165163")
                .numeroPedido("1651652")
                .itens(List.of(getWrapperItemMock()))
                .valorTotal(Double.valueOf("100.00"))
                .mensagemPedido("")
                .build();
    }

    private static PedidoResponseDto.WrapperItemDto getWrapperItemMock() {
        return PedidoResponseDto.WrapperItemDto.builder()
                .id("13541")
                .nome("Pedido Teste")
                .categoria(ACOMPANHAMENTO)
                .preco(Double.valueOf("100.00"))
                .descricao("Pedido de teste")
                .build();
    }
}
