package pos.fiap.pedidos.adapter.in;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pos.fiap.pedidos.adapter.in.api.PedidoController;
import pos.fiap.pedidos.adapter.in.api.dto.PedidoRequestDto;
import pos.fiap.pedidos.adapter.in.api.mapper.PedidoDtoMapper;
import pos.fiap.pedidos.domain.model.DadosPedido;
import pos.fiap.pedidos.port.PedidoUseCasePort;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static pos.fiap.pedidos.objectmother.dtos.in.PedidoRequestDtoObjectMother.getPedidoRequestDtoMock;
import static pos.fiap.pedidos.objectmother.dtos.in.PedidoResponseDtoObjectMother.getPedidoResponseDtoMock;
import static pos.fiap.pedidos.objectmother.model.DadosPedidoObjectMother.getDadosPedidoMock;
import static pos.fiap.pedidos.utils.JsonStringUtils.asJsonString;

@WebMvcTest(PedidoController.class)
class PedidoControllerTest {
    private static final String ENDPOINT_PEDIDO = "/api/v1/pedido";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoDtoMapper pedidoDtoMapper;

    @MockBean
    private PedidoUseCasePort pedidoUseCasePort;

    @Test
    @SneakyThrows
    void testRealizar_Success() {
        final var pedidoRequestDto = getPedidoRequestDtoMock();
        final var dadosPedido = getDadosPedidoMock();
        final var pedidoResponseDto = getPedidoResponseDtoMock();

        when(pedidoDtoMapper.toDadosPedidoFromRequestDto(any(PedidoRequestDto.class))).thenReturn(dadosPedido);
        when(pedidoUseCasePort.realizar(any(DadosPedido.class))).thenReturn(dadosPedido);
        when(pedidoDtoMapper.toPedidoResponseDtoFromDadosPedido(any(DadosPedido.class))).thenReturn(pedidoResponseDto);

        var result = mockMvc.perform(post(ENDPOINT_PEDIDO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(pedidoRequestDto)))
                .andReturn();

        var responseMvc = result.getResponse();
        verify(pedidoUseCasePort, times(1)).realizar(any(DadosPedido.class));
        assertEquals(HttpStatus.CREATED.value(), responseMvc.getStatus());
        assertEquals(asJsonString(pedidoResponseDto), result.getResponse().getContentAsString());
    }

    @Test
    @SneakyThrows
    void testListar_Success() {
        final var dadosPedidos = List.of(getDadosPedidoMock());
        final var pedidoResponseDto = List.of(getPedidoResponseDtoMock());

        when(pedidoUseCasePort.listar()).thenReturn(dadosPedidos);
        when(pedidoDtoMapper.toListPedidoResponseDtoFromListDadosPedido(anyList())).thenReturn(pedidoResponseDto);

        var result = mockMvc.perform(get(ENDPOINT_PEDIDO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var responseMvc = result.getResponse();
        verify(pedidoUseCasePort, times(1)).listar();
        assertEquals(HttpStatus.OK.value(), responseMvc.getStatus());
        assertEquals(asJsonString(pedidoResponseDto), result.getResponse().getContentAsString());
    }

    @Test
    @SneakyThrows
    void testListarNotFound_Success() {
        when(pedidoUseCasePort.listar()).thenReturn(Collections.emptyList());

        var result = mockMvc.perform(get(ENDPOINT_PEDIDO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var responseMvc = result.getResponse();
        verify(pedidoUseCasePort, times(1)).listar();
        assertEquals(HttpStatus.NOT_FOUND.value(), responseMvc.getStatus());
    }

    @Test
    @SneakyThrows
    void testAtualizar_Success() {
        final var pedidoRequestDto = getPedidoRequestDtoMock();
        pedidoRequestDto.setCpfCliente("123456");
        final var dadosPedidos = getDadosPedidoMock();
        dadosPedidos.setCpfCliente("123456");
        final var pedidoResponseDto = getPedidoResponseDtoMock();
        pedidoResponseDto.setCpfCliente("123456");

        when(pedidoDtoMapper.toDadosPedidoFromRequestDto(any(PedidoRequestDto.class))).thenReturn(getDadosPedidoMock());
        when(pedidoUseCasePort.atualizar(anyString(), any(DadosPedido.class))).thenReturn(dadosPedidos);
        when(pedidoDtoMapper.toPedidoResponseDtoFromDadosPedido(any(DadosPedido.class))).thenReturn(pedidoResponseDto);

        var result = mockMvc.perform(patch(ENDPOINT_PEDIDO.concat("/1234"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(pedidoRequestDto)))
                .andReturn();

        var responseMvc = result.getResponse();
        verify(pedidoUseCasePort, times(1)).atualizar(anyString(), any(DadosPedido.class));
        assertEquals(HttpStatus.OK.value(), responseMvc.getStatus());
        assertEquals(asJsonString(pedidoResponseDto), result.getResponse().getContentAsString());
    }
}
