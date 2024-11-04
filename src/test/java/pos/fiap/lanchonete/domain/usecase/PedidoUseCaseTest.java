package pos.fiap.lanchonete.domain.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pos.fiap.lanchonete.domain.model.DadosPedido;
import pos.fiap.lanchonete.domain.model.entity.Pedido;
import pos.fiap.lanchonete.domain.model.entity.mapper.PedidoMapper;
import pos.fiap.lanchonete.port.PedidoDbAdapterPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pos.fiap.lanchonete.objectmother.model.DadosPedidoObjectMother.getDadosPedidoMock;
import static pos.fiap.lanchonete.objectmother.model.PedidoObjectMother.getPedidoMock;

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
    void givenPedido_whenSendDadosPedido_thenSucceed() {
        when(pedidoDbAdapterPort.cadastrarPedido(any(Pedido.class))).thenReturn(pedido);

        var dadosPedido = pedidoUseCase.realizar(getDadosPedidoMock());

        verify(pedidoDbAdapterPort, times(1)).cadastrarPedido(any(Pedido.class));
        verify(pedidoMapper, times(1)).fromDadosPedido(anyDouble(), any(DadosPedido.class));
        verify(pedidoMapper, times(1)).toDadosPedido(any(Pedido.class));
        assertNotNull(dadosPedido);
        assertFalse(dadosPedido.getNumeroPedido().isEmpty());
    }

    @Test
    void givenPedidos_whenGetAll_thenSucceed() {
        when(pedidoDbAdapterPort.buscarPedidos()).thenReturn(List.of(pedido));

        var pedidos = pedidoUseCase.listar();

        verify(pedidoDbAdapterPort, times(1)).buscarPedidos();
        verify(pedidoMapper, times(1)).toListDadosPedido(anyList());
        assertNotNull(pedidos);
        assertFalse(pedidos.isEmpty());
    }
}
