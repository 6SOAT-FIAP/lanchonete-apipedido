package pos.fiap.pedidos.adapter.out.api.produto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import pos.fiap.pedidos.adapter.out.api.produto.dto.ProdutoResponseDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pos.fiap.pedidos.objectmother.ProdutoObjectMother.getProdutoResponseDtoMock;

@ExtendWith(MockitoExtension.class)
class ApiProdutoAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ApiProdutoAdapter apiProdutoAdapter;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(apiProdutoAdapter, "urlPedido", "urlPedido");
    }

    @Test
    void testBuscarProdutosPorIds_Success() {
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                eq(new ParameterizedTypeReference<List<ProdutoResponseDto>>() {
                })))
                .thenReturn(new ResponseEntity<>(List.of(getProdutoResponseDtoMock()), HttpStatus.OK));

        var produtos = apiProdutoAdapter.buscarProdutosPorIds("1234");

        assertNotNull(produtos);
    }

    @Test
    void testGerarPagamentoQRCode_Error() {
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                eq(new ParameterizedTypeReference<List<ProdutoResponseDto>>() {
                })))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

        var produtos = apiProdutoAdapter.buscarProdutosPorIds("1234");
        assertNotNull(produtos);
        assertTrue(produtos.isEmpty());
    }
}
