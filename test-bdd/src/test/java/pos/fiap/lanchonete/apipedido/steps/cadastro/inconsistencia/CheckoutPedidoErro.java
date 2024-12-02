package pos.fiap.lanchonete.apipedido.steps.cadastro.inconsistencia;

import io.cucumber.java.en.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.Assert.*;

public class CheckoutPedidoErro {

    private String payload;
    private HttpResponse<String> response;

    @Given("o seguinte payload inconsistente para realizar o checkout do pedido:")
    public void o_seguinte_payload_inconsistente_para_realizar_o_checkout(String body) {
        payload = body;
    }

    @When("envio uma requisição POST para {string} com os dados do pedido inconsistente")
    public void envio_uma_requisicao_post_para(String endpoint) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082" + endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Then("o código de resposta deve ser {int} para erro no pedido")
    public void o_codigo_de_resposta_deve_ser(int statusCode) {
        assertNotNull(response);
        assertEquals(statusCode, response.statusCode());
    }
}
