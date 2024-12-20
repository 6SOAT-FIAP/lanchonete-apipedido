package pos.fiap.pedidos.adapter.out.database;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pos.fiap.pedidos.adapter.out.database.entities.PedidoEntity;
import pos.fiap.pedidos.adapter.out.database.entities.mapper.PedidoEntityMapper;
import pos.fiap.pedidos.adapter.out.database.repository.PedidoRepository;
import pos.fiap.pedidos.adapter.out.exception.PedidoNotFoundException;
import pos.fiap.pedidos.domain.model.entity.Pedido;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pos.fiap.pedidos.objectmother.PedidoObjectMother.*;

@ExtendWith(MockitoExtension.class)
class PedidoDbAdapterTest {

    @Mock
    private PedidoRepository pedidoRepository;
    @Spy
    private PedidoEntityMapper pedidoEntityMapper = Mappers.getMapper(PedidoEntityMapper.class);
    @InjectMocks
    private PedidoDbAdapter pedidoDbAdapter;

    @Test
    void testCadastrarPedido_Success() {
        when(pedidoRepository.save(any(PedidoEntity.class))).thenReturn(getPedidoEntityMock());

        var pedido = pedidoDbAdapter.cadastrarPedido(getPedidoMock());

        verify(pedidoRepository, times(1)).save(any(PedidoEntity.class));
        verify(pedidoEntityMapper, times(1)).toEntity(any(Pedido.class));
        assertNotNull(pedido);
    }

    @Test
    void testBuscarPedidos_Success() {
        when(pedidoRepository.findAll())
                .thenReturn(List.of(getPedidoEntityMock(), getPedidoEntityStatusFinalizadoMock(),
                        getPedidoEntityStatusNullMock()));

        var pedido = pedidoDbAdapter.buscarPedidos();

        verify(pedidoRepository, times(1)).findAll();
        verify(pedidoEntityMapper, times(1)).toListPedido(anyList());
        assertNotNull(pedido);
        assertFalse(pedido.isEmpty());
    }

    @Test
    void testObterPedidoPorId_Success() {
        when(pedidoRepository.findById(anyString())).thenReturn(Optional.ofNullable(getPedidoEntityMock()));

        var pedido = pedidoDbAdapter.obterPedidoPorId("123");

        verify(pedidoRepository, times(1)).findById(anyString());
        verify(pedidoEntityMapper, times(1)).toPedido(any(PedidoEntity.class));
        assertNotNull(pedido);
    }

    @Test
    void testObterPedidoPorId_PedidoNotFoundException() {
        when(pedidoRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(PedidoNotFoundException.class, () -> {
            pedidoDbAdapter.obterPedidoPorId("123");
        });
    }
}
