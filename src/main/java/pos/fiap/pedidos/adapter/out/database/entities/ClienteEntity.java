package pos.fiap.pedidos.adapter.out.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//@Data
//@Builder
//@Entity(name = "tb_cliente")
//public class ClienteEntity implements Serializable {
//    @Serial
//    private static final long serialVersionUID = -4367228802967438807L;
//
//    @Id
//    private String cpf;
//    private String nome;
//    private String email;
//
//    @Builder.Default
//    @OneToMany(mappedBy = "clienteEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnoreProperties("cliente")
//    private List<PedidoEntity> pedidos = new ArrayList<>();
//}
