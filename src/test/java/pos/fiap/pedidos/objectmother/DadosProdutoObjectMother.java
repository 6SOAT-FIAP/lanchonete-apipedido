package pos.fiap.pedidos.objectmother;

import pos.fiap.pedidos.domain.model.DadosProduto;

import static pos.fiap.pedidos.domain.enums.CategoriaEnum.ACOMPANHAMENTO;

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
