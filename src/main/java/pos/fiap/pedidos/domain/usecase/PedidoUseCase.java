package pos.fiap.pedidos.domain.usecase;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pos.fiap.pedidos.adapter.out.api.produto.dto.ProdutoResponseDto;
import pos.fiap.pedidos.adapter.out.exception.RecursoNaoEncontradoException;
import pos.fiap.pedidos.domain.enums.CategoriaEnum;
import pos.fiap.pedidos.domain.model.DadosPedido;
import pos.fiap.pedidos.domain.model.DadosProduto;
import pos.fiap.pedidos.domain.model.entity.Produto;
import pos.fiap.pedidos.domain.model.entity.mapper.PedidoMapper;
import pos.fiap.pedidos.port.PagamentoAdapterPort;
import pos.fiap.pedidos.port.PedidoDbAdapterPort;
import pos.fiap.pedidos.port.PedidoUseCasePort;
import pos.fiap.pedidos.port.ProdutoAdapterPort;

import java.time.LocalDateTime;
import java.util.List;

import static pos.fiap.pedidos.domain.model.DadosPedido.ordenarPedidos;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoUseCase implements PedidoUseCasePort {

    private final PedidoMapper pedidoMapper;
    private final PedidoDbAdapterPort pedidoDbAdapterPort;
    private final ProdutoAdapterPort produtoAdapterPort;
    private final PagamentoAdapterPort pagamentoAdapterPort;

    @Override
    public DadosPedido realizar(DadosPedido dadosPedido) {
        final var produtos = obterDadosDoProduto(dadosPedido.getItens().stream().map(DadosProduto::getId).toList());

        if (produtos.isEmpty()) {
            throw new RecursoNaoEncontradoException("Não foram encontrados os produtos informados");
        }

        dadosPedido.calculaValorPedido(produtos);

        var pedido = pedidoMapper.fromDadosPedido(dadosPedido);

        var pedidoResponse = pedidoDbAdapterPort.cadastrarPedido(pedido);

        var qrCode = pagamentoAdapterPort.realizarPagamento(pedidoResponse);

        // mapear qrcode para response

        return pedidoMapper.toDadosPedido(pedidoResponse);
    }

    @Override
    public List<DadosPedido> listar() {
        final var pedidos = pedidoDbAdapterPort.buscarPedidos();

        pedidos.forEach(pedido -> {
            final var dadosProdutos = obterDadosDoProduto(pedido.getItens().stream().map(Produto::getId).toList());
            pedido.setItens(dadosProdutos.stream().map(dadosProduto ->
                    Produto.builder()
                            .id(dadosProduto.getId())
                            .nome(dadosProduto.getNome())
                            .descricao(dadosProduto.getDescricao())
                            .preco(dadosProduto.getPreco())
                            .categoria(dadosProduto.getCategoria())
                            .imagem(dadosProduto.getImagem())
                            .build()
            ).toList());
        });

        var dadosPedido = pedidoMapper.toListDadosPedido(pedidos);
        return ordenarPedidos(dadosPedido);
    }

    @Override
    @SneakyThrows
    public DadosPedido obterPedidoPorId(String idPedido) {
        final var pedido = pedidoDbAdapterPort.obterPedidoPorId(idPedido);
        final var dadosProdutos = obterDadosDoProduto(pedido.getItens().stream().map(Produto::getId).toList());

        pedido.setItens(dadosProdutos.stream().map(dadosProduto ->
                        Produto.builder()
                                .id(dadosProduto.getId())
                                .nome(dadosProduto.getNome())
                                .descricao(dadosProduto.getDescricao())
                                .preco(dadosProduto.getPreco())
                                .categoria(dadosProduto.getCategoria())
                                .imagem(dadosProduto.getImagem())
                                .build())
                .toList());
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

    private List<DadosProduto> obterDadosDoProduto(List<String> itensId) {
        try {
            String idsParam = String.join("&ids=", itensId);

            List<ProdutoResponseDto> response = produtoAdapterPort.buscarProdutosPorIds(idsParam);

            return response.stream()
                    .map(produto -> DadosProduto.builder()
                            .id(produto.getId())
                            .nome(produto.getNome())
                            .categoria(CategoriaEnum.valueOf(produto.getCategoria()))
                            .preco(produto.getPreco())
                            .descricao(produto.getDescricao())
                            .imagem(produto.getImagem())
                            .build())
                    .toList();
        } catch (Exception e) {
            log.error("Erro ao obter dados dos produtos", e);
            throw new RuntimeException("Erro ao obter dados dos produtos", e);
        }
    }
}