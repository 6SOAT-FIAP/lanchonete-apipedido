package pos.fiap.lanchonete.objectmother.model;

import pos.fiap.lanchonete.domain.model.DadosProduto;

import static pos.fiap.lanchonete.domain.enums.CategoriaEnum.ACOMPANHAMENTO;

public class DadosProdutoObjectMother {

    public static DadosProduto getDadosProdutoMock() {
        return DadosProduto.builder()
                .id("13541")
                .nome("Pedido Teste")
                .categoria(ACOMPANHAMENTO)
                .preco(Double.valueOf("100.00"))
                .descricao("Pedido de teste")
                .build();
    }
}
