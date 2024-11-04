package pos.fiap.lanchonete.domain.usecase;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pos.fiap.lanchonete.domain.model.DadosPedido;
import pos.fiap.lanchonete.domain.model.entity.mapper.PedidoMapper;
import pos.fiap.lanchonete.port.PedidoDbAdapterPort;
import pos.fiap.lanchonete.port.PedidoUseCasePort;

import java.time.LocalDateTime;
import java.util.List;

import static pos.fiap.lanchonete.domain.model.DadosPedido.ordenarPedidos;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoUseCase implements PedidoUseCasePort {

    private final PedidoMapper pedidoMapper;
    private final PedidoDbAdapterPort pedidoDbAdapterPort;

    @Override
    public DadosPedido realizar(DadosPedido dadosPedido) {
        var valorTotal = dadosPedido.calculaValorPedido();
        var pedido = pedidoMapper.fromDadosPedido(valorTotal, dadosPedido);
        var pedidoResponse = pedidoDbAdapterPort.cadastrarPedido(pedido);
        return pedidoMapper.toDadosPedido(pedidoResponse);
    }

    @Override
    public List<DadosPedido> listar() {
        var pedidos = pedidoDbAdapterPort.buscarPedidos();
        var dadosPedido = pedidoMapper.toListDadosPedido(pedidos);
        return ordenarPedidos(dadosPedido);
    }

    @Override
    @SneakyThrows
    public DadosPedido obterPedidoPorId(String idPedido) {
        var pedido = pedidoDbAdapterPort.obterPedidoPorId(idPedido);
        return pedidoMapper.toDadosPedido(pedido);
    }

    @Override
    public DadosPedido atualizar(String idPedido, DadosPedido dadosPedido) {
        var pedido = pedidoDbAdapterPort.obterPedidoPorId(idPedido);
        pedido.setStatusPedido(dadosPedido.getStatusPedido());
        pedido.setDataAtualizacao(LocalDateTime.now());

        var pedidoResponse = pedidoDbAdapterPort.cadastrarPedido(pedido);
        return pedidoMapper.toDadosPedido(pedidoResponse);
    }

}
