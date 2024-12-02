package pos.fiap.pedidos.adapter.out.api.produto.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.net.URL;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProdutoResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 6225223729285356703L;
    private String id;
    private String nome;
    private String categoria;
    private Double preco;
    private String descricao;
    private URL imagem;
}
