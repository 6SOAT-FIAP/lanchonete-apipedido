package pos.fiap.pedidos.adapter.out.api.pagamento;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pos.fiap.pedidos.adapter.out.api.pagamento.dto.PagamentoRequestDto;
import pos.fiap.pedidos.adapter.out.api.pagamento.dto.PagamentoResponseDto;
import pos.fiap.pedidos.domain.model.entity.Pedido;
import pos.fiap.pedidos.port.PagamentoAdapterPort;

@Component
@RequiredArgsConstructor
public class PagamentoAdapter implements PagamentoAdapterPort {
    @Value("${api.pagamento.url}")
    private String urlPagamento;
    private final RestTemplate restTemplate;

    @Override
    public String realizarPagamento(Pedido pedido) {
        PagamentoRequestDto pagamentoRequest = toPagamentoRequest(pedido);

        try {
            HttpEntity<PagamentoRequestDto> request = new HttpEntity<>(pagamentoRequest);
            ResponseEntity<PagamentoResponseDto> response = restTemplate.postForEntity(
                    urlPagamento, request, PagamentoResponseDto.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Erro ao processar pagamento: " + response.getStatusCode());
            }

            return response.getBody().getQrCode();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar à API de pagamento", e);
        }
    }

    private PagamentoRequestDto toPagamentoRequest(Pedido pedido) {
        return PagamentoRequestDto.builder()
                .numeroPedido(pedido.getNumeroPedido())
                .metodoPagamento("PIX")
                .valorTotal(pedido.getValorTotal())
                .itens(pedido.getItens().stream()
                        .map(item -> PagamentoRequestDto.ItemPagamentoDto.builder()
                                .nome(item.getNome())
                                .categoria(item.getCategoria().name())
                                .preco(item.getPreco())
                                .descricao(item.getDescricao())
                                .build())
                        .toList())
                .build();
    }
}