package my.package.name

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
public class MyStepDefinitions {

    @Given("^a tarea$")
    public void a_tarea() throws Throwable {
        throw new PendingException();
    }

    @When("^I press \"([^\"]*)\"$")
    public void i_press_something(String strArg1) throws Throwable {
        throw new PendingException();
    }

    @Then("^\"([^\"]*)\" is \"([^\"]*)\"$")
    public void something_is_something(String strArg1, String strArg2) throws Throwable {
        throw new PendingException();
    }

}
