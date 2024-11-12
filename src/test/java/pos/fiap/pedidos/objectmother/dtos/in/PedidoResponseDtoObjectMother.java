package pos.fiap.pedidos.objectmother.dtos.in;

import pos.fiap.pedidos.adapter.in.api.dto.PedidoResponseDto;

import java.util.List;

import static pos.fiap.pedidos.domain.enums.CategoriaEnum.ACOMPANHAMENTO;

public class PedidoResponseDtoObjectMother {

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
