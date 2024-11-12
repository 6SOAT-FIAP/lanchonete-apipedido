package pos.fiap.lanchonete.domain.model.entity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pos.fiap.lanchonete.domain.model.DadosPedido;
import pos.fiap.lanchonete.domain.model.entity.Pedido;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface PedidoMapper {

    @Mapping(target = "dadosPedido.itens")
    @Mapping(target = "valorTotal", source = "valorTotal")
    @Mapping(target = "dataCriacao", qualifiedByName = "obterData")
    Pedido fromDadosPedido(Double valorTotal, DadosPedido dadosPedido);

    DadosPedido toDadosPedido(Pedido pedido);

    @Mapping(target = "itens", source = "pedido.itens")
    List<DadosPedido> toListDadosPedido(List<Pedido> pedidos);

    @Named("obterData")
    static LocalDateTime obterData(LocalDateTime data) {
        return LocalDateTime.now();
    }

}