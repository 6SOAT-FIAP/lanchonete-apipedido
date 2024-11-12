package pos.fiap.lanchonete.port;

import pos.fiap.lanchonete.domain.model.entity.Pedido;

import java.util.List;

public interface PedidoDbAdapterPort {

    Pedido cadastrarPedido(Pedido pedido);

    List<Pedido> buscarPedidos();

    Pedido obterPedidoPorId(String id);
}
