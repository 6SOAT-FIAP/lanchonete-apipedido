package pos.fiap.lanchonete.port;

import pos.fiap.lanchonete.domain.model.DadosPedido;

import java.util.List;

public interface PedidoUseCasePort {

    DadosPedido realizar(DadosPedido dadosPedido);

    List<DadosPedido> listar();

    DadosPedido obterPedidoPorId(String idPedido);

    DadosPedido atualizar(String idPedido, DadosPedido dadosPedido);
}
