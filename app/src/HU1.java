package my.package.name

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
public class MyStepDefinitions {

    @Given("^una \"([^\"]*)\" que se ha abierto/seleccionado para ver$")
    public void una_something_que_se_ha_abiertoseleccionado_para_ver(String strArg1) throws Throwable {
        throw new PendingException();
    }

    @When("^pulso un botón de \"([^\"]*)\" en la \"([^\"]*)\"$")
    public void pulso_un_botn_de_something_en_la_something(String strArg1, String strArg2) throws Throwable {
        throw new PendingException();
    }

    @Then("^la \"([^\"]*)\" es favorita$")
    public void la_something_es_favorita(String strArg1) throws Throwable {
        throw new PendingException();
    }

}
