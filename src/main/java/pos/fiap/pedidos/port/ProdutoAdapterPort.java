package pos.fiap.pedidos.port;

import pos.fiap.pedidos.adapter.out.api.produto.dto.ProdutoResponseDto;

import java.util.List;

public interface ProdutoAdapterPort {
    List<ProdutoResponseDto> buscarProdutosPorIds(String idsParam);
}
