package pos.fiap.pedidos.adapter.out.exception;

public class PedidoNotFoundException extends RuntimeException {
    public PedidoNotFoundException(String mensagem) {
        super(mensagem);
    }
}
