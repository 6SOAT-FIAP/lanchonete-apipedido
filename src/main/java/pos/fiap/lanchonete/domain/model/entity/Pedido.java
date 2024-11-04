package pos.fiap.lanchonete.domain.model.entity;

import lombok.Builder;
import lombok.Data;
import pos.fiap.lanchonete.domain.enums.StatusPedidoEnum;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Pedido implements Serializable {

    @Serial
    private static final long serialVersionUID = -7276864513373453028L;
    private String numeroPedido;
    private String mensagemPedido;
    private String cpfCliente;
    private Double valorTotal;
    private List<Produto> itens;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private StatusPedidoEnum statusPedido;
}
