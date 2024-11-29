package pos.fiap.pedidos.port;

import pos.fiap.pedidos.domain.model.entity.Produto;

public interface ProdutoAdapterPort {

    Produto buscarProdutoPorId(String id);
}
