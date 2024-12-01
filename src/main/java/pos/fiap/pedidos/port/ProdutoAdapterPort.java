package pos.fiap.pedidos.port;

import pos.fiap.pedidos.domain.model.entity.Produto;

import java.util.Optional;

public interface ProdutoAdapterPort {

    Optional<Produto> buscarProdutoPorId(String id);
}
