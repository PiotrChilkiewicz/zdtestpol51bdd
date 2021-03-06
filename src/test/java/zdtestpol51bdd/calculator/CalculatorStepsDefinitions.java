package zdtestpol51bdd.calculator;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class CalculatorStepsDefinitions {

    //Calculator - klasa - czyli przepis, jak powinien wyglądać obiekt
    //calculator - pole - PUSTE stworzony za pomocą przepisu z klasy Calculator
    Calculator calculator;
    Integer result;

    @Given("I have calculator")
    public void i_have_calculator() {
        //utwórz nowy obiekt klasy Calculator

        //new Calculator - stówrz nowy obiekt Calculator
        calculator=new Calculator();
     }
    @When("I add {int} and {int}")
    public void i_add_and(Integer int1, Integer int2) {
        result = calculator.addTwoNumbers(int1,int2);
    }
    @When("I subtraction {int} by {int}")
    public void i_subtraction_by(Integer int1, Integer int2) {
        result = calculator.substractionTwoNumbers(int1, int2);
    }
    @When("I division {int} by {int}")
    public void i_division_by(Integer int1, Integer int2) {
        result = calculator.divisionTwoNumbers(int1,int2);
    }
    @When("I multiplication {int} and {int}")
    public void i_multiplication_and(Integer int1, Integer int2) {
        result = calculator.multiplicationTwoNumbers(int1,int2);
    }

    @Then("I should get {int}")
    public void i_should_get(Integer int1) {
        Assert.assertEquals(result,int1);
    }

}
