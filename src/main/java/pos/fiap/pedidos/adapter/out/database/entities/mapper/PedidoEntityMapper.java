package pos.fiap.pedidos.adapter.out.database.entities.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pos.fiap.pedidos.adapter.out.database.entities.PedidoEntity;
import pos.fiap.pedidos.domain.model.entity.Pedido;
import pos.fiap.pedidos.domain.model.entity.Produto;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PedidoEntityMapper {

    @Mapping(target = "numeroPedido", defaultExpression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "itens", qualifiedByName = "obterIdProdutos")
    PedidoEntity toEntity(Pedido pedido);

    @Mapping(target = "itens", qualifiedByName = "preencherIdProdutos")
    Pedido toPedido(PedidoEntity entity);

    List<Pedido> toListPedido(List<PedidoEntity> entity);

    @Named("obterIdProdutos")
    static List<String> obterIdProdutos(List<Produto> itens) {
        return itens.stream().map(Produto::getId).toList();
    }

    @Named("preencherIdProdutos")
    static List<Produto> preencherIdProdutos(List<String> itens) {
        return itens.stream().map(item -> Produto.builder().id(item).build()).toList();
    }
}
