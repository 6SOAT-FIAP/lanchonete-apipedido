package pos.fiap.pedidos.port;

import pos.fiap.pedidos.domain.model.entity.Pedido;

public interface PagamentoAdapterPort {
    String realizarPagamento(Pedido pedido);
}
