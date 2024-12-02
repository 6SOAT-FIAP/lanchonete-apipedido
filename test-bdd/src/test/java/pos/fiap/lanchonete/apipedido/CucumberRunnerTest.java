package pos.fiap.lanchonete.apipedido;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "pos.fiap.lanchonete.apipedido.steps",
        plugin = {"pretty", "html:target/cucumber-reports"}
)
public class CucumberRunnerTest {

}
