package pos.fiap.lanchonete.adapter.in.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.fiap.lanchonete.adapter.in.api.dto.PedidoRequestDto;
import pos.fiap.lanchonete.adapter.in.api.dto.PedidoResponseDto;
import pos.fiap.lanchonete.adapter.in.api.mapper.PedidoDtoMapper;
import pos.fiap.lanchonete.domain.enums.StatusPedidoEnum;
import pos.fiap.lanchonete.port.PedidoUseCasePort;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Pedido", description = "APIs referente aos Pedidos")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/pedido")
@RestController
public class PedidoController {

    private final PedidoDtoMapper dtoMapper;
    private final PedidoUseCasePort pedidoUseCasePort;

    @Operation(summary = "Cadastrar um pedido",
            description = "Cadastra um pedido informando o cpf do cliente e a lista de itens")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = PedidoResponseDto.class),
                            mediaType = APPLICATION_JSON_VALUE)
            }),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @ResponseStatus(CREATED)
    @PostMapping
    public ResponseEntity<PedidoResponseDto> realizar(@RequestBody PedidoRequestDto pedidoRequestDto) {
        log.info("Pedido request, itens: {}", pedidoRequestDto);

        var dadosPedido = dtoMapper.toDadosPedidoFromRequestDto(pedidoRequestDto);
        dadosPedido.setStatusPedido(StatusPedidoEnum.RECEBIDO);

        var pedido = pedidoUseCasePort.realizar(dadosPedido);

        var pedidoResponse = dtoMapper.toPedidoResponseDtoFromDadosPedido(pedido);

        log.info("Pedido enviado para a fila: {}", dadosPedido);
        return ResponseEntity.status(CREATED).body(pedidoResponse);
    }

    @Operation(summary = "Listar pedidos",
            description = "Lista os pedidos criados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = PedidoResponseDto.class),
                            mediaType = APPLICATION_JSON_VALUE)
            }),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @ResponseStatus(OK)
    @GetMapping
    public ResponseEntity<List<PedidoResponseDto>> listar() {
        log.info("Listando pedido");

        var pedidos = pedidoUseCasePort.listar();

        if (pedidos.isEmpty()) {
            log.info("Não foram encontrados pedidos");
            return ResponseEntity.notFound().build();
        }

        var pedidoResponse = dtoMapper.toListPedidoResponseDtoFromListDadosPedido(pedidos);

        log.info("Fim listagem do pedido");
        return ResponseEntity.ok().body(pedidoResponse);
    }

    @Operation(summary = "Atualizar pedido",
            description = "Atualizar o pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = PedidoResponseDto.class),
                            mediaType = APPLICATION_JSON_VALUE)
            }),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @ResponseStatus(OK)
    @PatchMapping("/{idPedido}")
    public ResponseEntity<PedidoResponseDto> atualizar(@PathVariable String idPedido, @RequestBody PedidoRequestDto pedidoRequestDto) {
        log.info("Atualizando pedido");

        var dadosPedido = dtoMapper.toDadosPedidoFromRequestDto(pedidoRequestDto);

        var pedido = pedidoUseCasePort.atualizar(idPedido, dadosPedido);
        var pedidoResponse = dtoMapper.toPedidoResponseDtoFromDadosPedido(pedido);

        log.info("Fim atualização do pedido");
        return ResponseEntity.status(OK).body(pedidoResponse);
    }
}
