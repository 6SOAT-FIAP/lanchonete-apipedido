package pos.fiap.pedidos.domain.usecase;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pos.fiap.pedidos.adapter.out.exception.RecursoNaoEncontradoException;
import pos.fiap.pedidos.domain.model.DadosPedido;
import pos.fiap.pedidos.domain.model.DadosProduto;
import pos.fiap.pedidos.domain.model.entity.mapper.PedidoMapper;
import pos.fiap.pedidos.port.PedidoDbAdapterPort;
import pos.fiap.pedidos.port.PedidoUseCasePort;
import pos.fiap.pedidos.port.ProdutoAdapterPort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static pos.fiap.pedidos.domain.model.DadosPedido.ordenarPedidos;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoUseCase implements PedidoUseCasePort {

    private final PedidoMapper pedidoMapper;
    private final PedidoDbAdapterPort pedidoDbAdapterPort;
    private final ProdutoAdapterPort produtoAdapterPort;

    @Override
    public DadosPedido realizar(DadosPedido dadosPedido) {
        obterDadosDoProduto(dadosPedido);

        if (dadosPedido.getItens().isEmpty()) {
            throw new RecursoNaoEncontradoException("Não foram encontrados os produtos informados");
        }

        var valorTotal = dadosPedido.calculaValorPedido();

        var pedido = pedidoMapper.fromDadosPedido(valorTotal, dadosPedido);

        var pedidoResponse = pedidoDbAdapterPort.cadastrarPedido(pedido);

        return pedidoMapper.toDadosPedido(pedidoResponse);
    }

    private void obterDadosDoProduto(DadosPedido dadosPedido) {
        var produtos = new ArrayList<DadosProduto>();

        dadosPedido.getItens()
                .forEach(dadosProduto -> {
                    final var produto = produtoAdapterPort.buscarProdutoPorId(dadosProduto.getId());
                    produto.ifPresent(value -> produtos.add(DadosProduto.builder()
                            .id(value.getId())
                            .nome(value.getNome())
                            .categoria(value.getCategoria())
                            .preco(value.getPreco())
                            .descricao(value.getDescricao())
                            .imagem(value.getImagem())
                            .build()));
                });

        dadosPedido.setItens(produtos);
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
