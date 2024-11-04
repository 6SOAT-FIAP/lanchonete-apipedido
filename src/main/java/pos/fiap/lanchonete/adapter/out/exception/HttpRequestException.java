package pos.fiap.lanchonete.adapter.out.exception;

public class HttpRequestException extends RuntimeException {
    public HttpRequestException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
