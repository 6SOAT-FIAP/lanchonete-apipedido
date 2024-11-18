package pos.fiap.pedidos.adapter.in.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import pos.fiap.pedidos.domain.enums.CategoriaEnum;

import java.io.Serial;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PedidoResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -5675009956810195511L;
    private String cpfCliente;
    private String numeroPedido;
    private List<WrapperItemDto> itens;
    private Double valorTotal;
    private String mensagemPedido;
    private String qrCode;
    private String descricaoPedido;
    private LocalDateTime dataCriacao;


    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class WrapperItemDto implements Serializable {
        @Serial
        private static final long serialVersionUID = -3810204067053229994L;
        private String id;
        private String nome;
        private CategoriaEnum categoria;
        private Double preco;
        private String descricao;
        private URL imagem;
    }
}
