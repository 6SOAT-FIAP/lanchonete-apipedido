package pos.fiap.lanchonete.apipedido.steps.cadastro.sucesso;

import io.cucumber.java.en.*;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.Assert.*;

public class CheckoutPedido {

    private String payload;
    private HttpResponse<String> response;

    @Given("o seguinte payload para realizar o checkout do pedido:")
    public void o_seguinte_payload_para_realizar_o_checkout(String body) {
        payload = body;
    }

    @When("envio uma requisição POST para {string} com os dados do pedido")
    public void envio_uma_requisicao_post_para(String endpoint) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082" + endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Then("o código de resposta deve ser {int} para realização do pedido")
    public void o_codigo_de_resposta_deve_ser(int statusCode) {
        assertNotNull(response);
        assertEquals(statusCode, response.statusCode());
    }

    @And("o corpo da resposta do pedido deve conter todos os campos não nulos")
    public void o_corpo_da_resposta_deve_conter_campos_nao_nulos() {
        JSONObject actualJson = new JSONObject(response.body());

        assertNotNull(actualJson.optString("cpfCliente", null));
        assertNotNull(actualJson.optString("numeroPedido", null));
        assertNotNull(actualJson.optJSONArray("itens"));
        for (int i = 0; i < actualJson.getJSONArray("itens").length(); i++) {
            JSONObject actualItem = actualJson.getJSONArray("itens").getJSONObject(i);
            assertNotNull(actualItem.optString("id", null));
            assertNotNull(actualItem.optString("nome", null));
            assertNotNull(actualItem.optString("categoria", null));
            assertNotNull(actualItem.optDouble("preco", Double.NaN));
            assertNotNull(actualItem.optString("descricao", null));
            assertNotNull(actualItem.optString("imagem", null));
        }
        assertNotNull(actualJson.optDouble("valorTotal", Double.NaN));
        assertNotNull(actualJson.optString("mensagemPedido", null));
        assertNotNull(actualJson.optString("qrCode", null));
        assertNotNull(actualJson.optString("descricaoPedido", null));
        assertNotNull(actualJson.optString("dataCriacao", null));
    }
}
