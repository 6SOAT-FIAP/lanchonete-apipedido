package pos.fiap.lanchonete.adapter.out.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity(name = "tb_cliente")
@AllArgsConstructor
@NoArgsConstructor
public class ClienteEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -4367228802967438807L;

    @Id
    private String cpf;
    private String nome;
    private String email;

    @OneToMany(mappedBy = "clienteEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("cliente")
    private List<PedidoEntity> pedidos = new ArrayList<>();
}
