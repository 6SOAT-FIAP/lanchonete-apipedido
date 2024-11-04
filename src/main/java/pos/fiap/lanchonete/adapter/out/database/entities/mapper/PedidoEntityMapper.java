package pos.fiap.lanchonete.adapter.out.database.entities.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pos.fiap.lanchonete.adapter.out.database.entities.PedidoEntity;
import pos.fiap.lanchonete.domain.model.entity.Pedido;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class)
public interface PedidoEntityMapper {

    @Mapping(target = "numeroPedido", defaultExpression = "java(UUID.randomUUID().toString())")
    PedidoEntity toEntity(Pedido pedido);

    @Mapping(target = "mensagemPedido", expression = "java(\"Pedido realizado com sucesso\")")
    Pedido toPedido(PedidoEntity entity);

    List<Pedido> toListPedido(List<PedidoEntity> entity);
}
