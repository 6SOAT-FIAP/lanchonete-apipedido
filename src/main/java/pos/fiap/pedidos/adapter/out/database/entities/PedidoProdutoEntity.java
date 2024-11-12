package pos.fiap.lanchonete.adapter.out.database.entities;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
//@Entity(name = "tb_pedido_produto")
public class PedidoProdutoEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 4341373822433962856L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
}
