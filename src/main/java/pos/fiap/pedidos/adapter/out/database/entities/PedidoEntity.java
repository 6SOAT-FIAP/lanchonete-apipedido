package pos.fiap.pedidos.adapter.out.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pos.fiap.pedidos.domain.enums.StatusPedidoEnum;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity(name = "tb_pedido")
@NoArgsConstructor
@AllArgsConstructor
public class PedidoEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1543215970660959979L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String numeroPedido;
    private String cpfCliente;
    private Double valorTotal;
    private List<String> itens = new ArrayList<>();
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    @Enumerated(EnumType.STRING)
    private StatusPedidoEnum statusPedido;
}
