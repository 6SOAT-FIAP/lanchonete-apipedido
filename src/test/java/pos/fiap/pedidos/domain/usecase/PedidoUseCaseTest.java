package pos.fiap.pedidos.domain.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pos.fiap.pedidos.domain.enums.StatusPedidoEnum;
import pos.fiap.pedidos.domain.model.DadosPedido;
import pos.fiap.pedidos.domain.model.entity.Pedido;
import pos.fiap.pedidos.domain.model.entity.mapper.PedidoMapper;
import pos.fiap.pedidos.port.PedidoDbAdapterPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pos.fiap.pedidos.objectmother.model.DadosPedidoObjectMother.getDadosPedidoMock;
import static pos.fiap.pedidos.objectmother.model.PedidoObjectMother.getPedidoMock;

@ExtendWith(MockitoExtension.class)
class PedidoUseCaseTest {

    @Mock
    private PedidoDbAdapterPort pedidoDbAdapterPort;
    @Spy
    private PedidoMapper pedidoMapper = Mappers.getMapper(PedidoMapper.class);
    @InjectMocks
    private PedidoUseCase pedidoUseCase;

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = getPedidoMock();
    }

    @Test
    void givenPedido_whenRealizar_thenSucceed() {
        when(pedidoDbAdapterPort.cadastrarPedido(any(Pedido.class))).thenReturn(pedido);

        var dadosPedido = pedidoUseCase.realizar(getDadosPedidoMock());

        verify(pedidoDbAdapterPort, times(1)).cadastrarPedido(any(Pedido.class));
        verify(pedidoMapper, times(1)).fromDadosPedido(anyDouble(), any(DadosPedido.class));
        verify(pedidoMapper, times(1)).toDadosPedido(any(Pedido.class));
        assertNotNull(dadosPedido);
        assertFalse(dadosPedido.getNumeroPedido().isEmpty());
    }

    @Test
    void givenPedidos_whenListar_thenSucceed() {
        when(pedidoDbAdapterPort.buscarPedidos()).thenReturn(List.of(pedido));

        var pedidos = pedidoUseCase.listar();

        verify(pedidoDbAdapterPort, times(1)).buscarPedidos();
        verify(pedidoMapper, times(1)).toListDadosPedido(anyList());
        assertNotNull(pedidos);
        assertFalse(pedidos.isEmpty());
    }

    @Test
    void givenPedidos_whenObterPedidoPorId_thenSucceed() {
        when(pedidoDbAdapterPort.obterPedidoPorId(anyString())).thenReturn(pedido);

        var pedidos = pedidoUseCase.obterPedidoPorId(pedido.getNumeroPedido());

        verify(pedidoDbAdapterPort, times(1)).obterPedidoPorId(anyString());
        verify(pedidoMapper, times(1)).toDadosPedido(any(Pedido.class));
        assertNotNull(pedidos);
    }

    @Test
    void givenPedidos_whenAtualizar_thenSucceed() {
        var pedidoAtualizado = pedido;
        pedidoAtualizado.setStatusPedido(StatusPedidoEnum.PRONTO);

        when(pedidoDbAdapterPort.obterPedidoPorId(anyString())).thenReturn(pedido);
        when(pedidoDbAdapterPort.cadastrarPedido(any(Pedido.class))).thenReturn(pedidoAtualizado);

        var pedidos = pedidoUseCase.atualizar(pedido.getNumeroPedido(), getDadosPedidoMock());

        verify(pedidoDbAdapterPort, times(1)).obterPedidoPorId(anyString());
        verify(pedidoDbAdapterPort, times(1)).cadastrarPedido(any(Pedido.class));
        verify(pedidoMapper, times(1)).toDadosPedido(any(Pedido.class));
        assertNotNull(pedidos);
    }
}
