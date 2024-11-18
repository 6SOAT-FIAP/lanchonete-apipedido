package pos.fiap.pedidos.adapter.in.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pos.fiap.pedidos.adapter.in.api.dto.PedidoRequestDto;
import pos.fiap.pedidos.adapter.in.api.dto.PedidoResponseDto;
import pos.fiap.pedidos.domain.model.DadosPedido;
import pos.fiap.pedidos.utils.Constantes;

import java.util.List;

@Mapper(componentModel = "spring", imports = Constantes.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PedidoDtoMapper {
    @Mapping(target = "itens", source = "pedidoRequest.itens")
    @Mapping(target = "cpfCliente", source = "pedidoRequest.cpfCliente")
    @Mapping(target = "statusPedido", source = "pedidoRequest.statusPedido")
    DadosPedido toDadosPedidoFromRequestDto(PedidoRequestDto pedidoRequest);

    @Mapping(target = "numeroPedido", source = "pedido.numeroPedido")
    @Mapping(target = "mensagemPedido", source = "pedido.mensagemPedido")
    @Mapping(target = "descricaoPedido", source = "pedido.statusPedido.label")
    PedidoResponseDto toPedidoResponseDtoFromDadosPedido(DadosPedido pedido);
    List<PedidoResponseDto> toListPedidoResponseDtoFromListDadosPedido(List<DadosPedido> pedidos);
}
