package pos.fiap.pedidos.objectmother;

import pos.fiap.pedidos.adapter.out.api.produto.dto.ProdutoResponseDto;
import pos.fiap.pedidos.domain.model.entity.Produto;

import static pos.fiap.pedidos.domain.enums.CategoriaEnum.ACOMPANHAMENTO;

public class ProdutoObjectMother {

    public static Produto getProdutoMock() {
        return Produto.builder()
                .id("6849851")
                .nome("Produto Teste")
                .categoria(ACOMPANHAMENTO)
                .preco(Double.valueOf("100.00"))
                .descricao("Produto de teste")
                .build();
    }

    public static ProdutoResponseDto getProdutoResponseDtoMock() {
        return ProdutoResponseDto.builder()
                .id("6849851")
                .nome("Produto Teste")
                .categoria(ACOMPANHAMENTO.getName())
                .preco(Double.valueOf("100.00"))
                .descricao("Produto de teste")
                .build();
    }
}
