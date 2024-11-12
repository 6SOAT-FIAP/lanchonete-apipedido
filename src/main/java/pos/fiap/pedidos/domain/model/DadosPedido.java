package pos.fiap.lanchonete.domain.model;

import lombok.Builder;
import lombok.Data;
import pos.fiap.lanchonete.domain.enums.StatusPedidoEnum;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static pos.fiap.lanchonete.domain.enums.StatusPedidoEnum.*;

@Data
@Builder
public class DadosPedido implements Serializable {

    @Serial
    private static final long serialVersionUID = 767704268070979122L;

    private String cpfCliente;
    private String numeroPedido;
    private List<DadosProduto> itens;
    private Double valorTotal;
    private String mensagemPedido;
    private String qrCode;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private StatusPedidoEnum statusPedido;

    public void atualizarData() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    public static List<DadosPedido> ordenarPedidos(List<DadosPedido> pedidos) {
        return pedidos.stream()
                .sorted(Comparator.comparing(DadosPedido::getStatusPedido, (status1, status2) -> {
                    List<StatusPedidoEnum> order = List.of(PRONTO, EM_PREPARACAO, RECEBIDO);
                    return Integer.compare(order.indexOf(status1), order.indexOf(status2));
                }).thenComparing(DadosPedido::getDataCriacao))
                .toList();
    }

    public Double calculaValorPedido() {
        return itens.stream().map(DadosProduto::getPreco).reduce(0.0, Double::sum);
    }
}
