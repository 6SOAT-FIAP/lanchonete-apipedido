package pos.fiap.pedidos.adapter.in.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import pos.fiap.pedidos.domain.enums.CategoriaEnum;
import pos.fiap.pedidos.domain.enums.StatusPedidoEnum;

import java.io.Serial;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PedidoRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -2674614054267148943L;
    private String cpfCliente;
    private StatusPedidoEnum statusPedido;
    private List<WrapperProdutoRequestDto> itens;


    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class WrapperProdutoRequestDto implements Serializable {
        @Serial
        private static final long serialVersionUID = -7187216715845710615L;
        private String id;
        private String nome;
        private CategoriaEnum categoria;
        private Double preco;
        private String descricao;
        private URL imagem;
    }
}
