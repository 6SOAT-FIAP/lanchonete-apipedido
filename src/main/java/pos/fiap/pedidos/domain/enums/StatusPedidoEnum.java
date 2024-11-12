package pos.fiap.lanchonete.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusPedidoEnum {
    PRONTO("Pronto"),
    EM_PREPARACAO("Em Preparação"),
    RECEBIDO("Recebido"),
    FINALIZADO("Finalizado");

    private final String label;
}