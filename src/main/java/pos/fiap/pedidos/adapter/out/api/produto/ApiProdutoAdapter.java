package pos.fiap.pedidos.adapter.out.api.produto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pos.fiap.pedidos.adapter.out.api.produto.dto.ProdutoResponseDto;
import pos.fiap.pedidos.adapter.out.exception.HttpRequestException;
import pos.fiap.pedidos.port.ProdutoAdapterPort;

import java.util.List;

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
    public List<ProdutoResponseDto> buscarProdutosPorIds(String idsParam) {
        try {
            log.info(String.format(STRING_LOG_FORMAT, SERVICE_NAME, "buscarProdutosPorIds", INICIO), idsParam);

            var response = restTemplate.exchange(
                    urlPedido.concat(String.format("/?ids=%s", idsParam)),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ProdutoResponseDto>>() {}
            );

            if (response.getStatusCode().value() == HttpStatus.NOT_FOUND.value() || isNull(response.getBody())) {
                log.info(String.format(STRING_LOG_FORMAT, SERVICE_NAME, "buscarProdutosPorIds", FIM),
                        "Nenhum produto encontrado para os IDs {}", idsParam);
                return List.of();
            }

            List<ProdutoResponseDto> produtos = response.getBody();
            log.info(String.format(STRING_LOG_FORMAT, SERVICE_NAME, "buscarProdutosPorIds", FIM), produtos);
            return produtos;
        } catch (Exception e) {
            log.error("Erro ao buscar produtos", e);
            throw new HttpRequestException("Erro ao buscar produtos", e);
        }
    }
}
