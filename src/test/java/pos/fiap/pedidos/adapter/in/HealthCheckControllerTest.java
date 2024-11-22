package pos.fiap.pedidos.adapter.in;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import pos.fiap.pedidos.adapter.in.api.HealthCheckController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(HealthCheckController.class)
class HealthCheckControllerTest {
    private static final String ENDPOINT = "/health";
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private HealthCheckController healthCheckController;

    @Test
    @SneakyThrows
    void testHealthCheck() {
        var result = mockMvc.perform(get(ENDPOINT)).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals("Healthy", result.getResponse().getContentAsString());
    }
}