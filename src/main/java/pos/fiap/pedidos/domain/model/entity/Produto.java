package pos.fiap.pedidos.domain.model.entity;

import lombok.Builder;
import lombok.Data;
import pos.fiap.pedidos.domain.enums.CategoriaEnum;

import java.io.Serial;
import java.io.Serializable;
import java.net.URL;

@Data
@Builder
public class Produto implements Serializable {
    @Serial
    private static final long serialVersionUID = 2257875953001651461L;
    private String id;
    private String nome;
    private CategoriaEnum categoria;
    private Double preco;
    private String descricao;
    private URL imagem;
}
