package pos.fiap.lanchonete.objectmother.model;

import pos.fiap.lanchonete.domain.model.entity.Produto;

import static pos.fiap.lanchonete.domain.enums.CategoriaEnum.ACOMPANHAMENTO;

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
}
