package pos.fiap.pedidos.objectmother;

import pos.fiap.pedidos.adapter.out.api.pagamento.dto.PagamentoResponseDto;

public class PagamentoObjectMother {

    public static PagamentoResponseDto getPagamentoResponseDtoMock(){
        return PagamentoResponseDto.builder().qrCode("1651").build();
    }
}
