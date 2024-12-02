package pos.fiap.pedidos.adapter.out.api.pagamento;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static pos.fiap.pedidos.objectmother.PagamentoObjectMother.getPagamentoResponseDtoMock;
import static pos.fiap.pedidos.objectmother.PedidoObjectMother.getPedidoMock;

@ExtendWith(MockitoExtension.class)
class PagamentoAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PagamentoAdapter pagamentoAdapter;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(pagamentoAdapter, "urlPagamento", "urlPagamento");
    }

    @Test
    void testRealizarPagamento_Success() {
        when(restTemplate.postForEntity(anyString(), any(), any()))
                .thenReturn(new ResponseEntity<>(getPagamentoResponseDtoMock(), HttpStatus.OK));

        var produtos = pagamentoAdapter.realizarPagamento(getPedidoMock());

        assertNotNull(produtos);
    }

    @Test
    void testGerarPagamentoQRCode_Error() {
        when(restTemplate.postForEntity(anyString(), any(), any()))
                .thenReturn(new ResponseEntity<>(getPagamentoResponseDtoMock(), HttpStatus.NOT_FOUND));

        assertThrows(RuntimeException.class, () -> {
            pagamentoAdapter.realizarPagamento(getPedidoMock());
        });
    }
}
