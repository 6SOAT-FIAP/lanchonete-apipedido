package pos.fiap.lanchonete.adapter.in.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pos.fiap.lanchonete.domain.enums.CategoriaEnum;
import pos.fiap.lanchonete.domain.enums.StatusPedidoEnum;

import java.io.Serial;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

@Getter
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PedidoRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -2674614054267148943L;
    private String cpfCliente;
    private StatusPedidoEnum statusPedido;
    private List<WrapperProdutoRequestDto> itens;


    @Getter
    @Builder
    @ToString
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
