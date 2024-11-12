package pos.fiap.lanchonete.objectmother.dtos.in;

import pos.fiap.lanchonete.adapter.in.api.dto.PedidoRequestDto;

import java.util.List;

import static pos.fiap.lanchonete.domain.enums.CategoriaEnum.ACOMPANHAMENTO;

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
                .nome("Pedido Teste")
                .categoria(ACOMPANHAMENTO)
                .preco(Double.valueOf("100.00"))
                .descricao("Pedido de teste")
                .build();
    }
}
