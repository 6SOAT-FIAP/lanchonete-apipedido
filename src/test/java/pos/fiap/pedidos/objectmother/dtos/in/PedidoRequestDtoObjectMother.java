package pos.fiap.pedidos.objectmother.dtos.in;

import pos.fiap.pedidos.adapter.in.api.dto.PedidoRequestDto;

import java.util.List;

public class PedidoRequestDtoObjectMother {

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
}
