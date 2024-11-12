package pos.fiap.lanchonete.adapter.out.database.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pos.fiap.lanchonete.domain.enums.CategoriaEnum;

import java.io.Serial;
import java.io.Serializable;
import java.net.URL;

@Data
@Builder
@Entity(name = "tb_produto")
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 4826642502026722957L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nome;

    @Enumerated(EnumType.STRING)
    private CategoriaEnum categoria;
    private Double preco;
    private String descricao;
    private URL imagem;
}
