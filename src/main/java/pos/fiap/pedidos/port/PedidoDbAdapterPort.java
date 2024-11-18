package pos.fiap.pedidos.port;

import pos.fiap.pedidos.domain.model.entity.Pedido;

import java.util.List;

public interface PedidoDbAdapterPort {

    Pedido cadastrarPedido(Pedido pedido);

    List<Pedido> buscarPedidos();

    Pedido obterPedidoPorId(String id);
}
