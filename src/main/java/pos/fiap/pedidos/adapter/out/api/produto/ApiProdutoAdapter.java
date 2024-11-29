package pos.fiap.pedidos.adapter.out.api.produto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pos.fiap.pedidos.adapter.out.api.produto.dto.ProdutoResponseDto;
import pos.fiap.pedidos.adapter.out.exception.HttpRequestException;
import pos.fiap.pedidos.adapter.out.exception.PedidoNotFoundException;
import pos.fiap.pedidos.domain.model.entity.Produto;
import pos.fiap.pedidos.port.ProdutoAdapterPort;

import static java.util.Objects.isNull;
import static pos.fiap.pedidos.utils.Constantes.FIM;
import static pos.fiap.pedidos.utils.Constantes.INICIO;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiProdutoAdapter implements ProdutoAdapterPort {
    private static final String SERVICE_NAME = "ApiProdutoAdapter";
    private static final String OBTER_PEDIDO_POR_ID_METHOD_NAME = "obterPedidoPorId";
    private static final String STRING_LOG_FORMAT = "%s_%s_%s {}";
    @Value("${api.produto.url}")
    private String urlPedido;
    private final RestTemplate restTemplate;

    @Override
    public Produto buscarProdutoPorId(String id) {
        try {
            log.info(String.format(STRING_LOG_FORMAT, SERVICE_NAME, OBTER_PEDIDO_POR_ID_METHOD_NAME, INICIO), id);

            var response = restTemplate.getForEntity(urlPedido.concat(String.format("/%s", id)),
                    ProdutoResponseDto.class);

            if (response.getStatusCode().value() == HttpStatus.NOT_FOUND.value() || isNull(response.getBody())) {
                log.info(String.format(STRING_LOG_FORMAT, SERVICE_NAME, OBTER_PEDIDO_POR_ID_METHOD_NAME, FIM),
                        "Não foi encontrado pedido para o id {}", id);
                throw new PedidoNotFoundException(String.format("Não foi encontrado pedido para o id %s", id));
            }

            var produtoResponseDto = response.getBody();

            var pedido = produtoResponseDto.toProduto();
            log.info(String.format(STRING_LOG_FORMAT, SERVICE_NAME, OBTER_PEDIDO_POR_ID_METHOD_NAME, FIM), pedido);
            return pedido;
        } catch (Exception e) {
            log.error("Ocorreu erro ao obter o produto. ", e);
            throw new HttpRequestException("Ocorreu erro ao obter o produto. ", e);
        }
    }
}
