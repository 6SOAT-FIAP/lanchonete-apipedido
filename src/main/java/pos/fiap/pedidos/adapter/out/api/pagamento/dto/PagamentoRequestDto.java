package pos.fiap.pedidos.adapter.out.api.pagamento.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PagamentoRequestDto {
    private String numeroPedido;
    private String metodoPagamento;
    private Double valorTotal;
    private List<ItemPagamentoDto> itens;

    @Data
    @Builder
    public static class ItemPagamentoDto {
        private String nome;
        private String categoria;
        private Double preco;
        private String descricao;
    }
}
