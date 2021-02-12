package cucumberTests;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DatabaseStepDefinition {

    @When("User clicks on SAVE")
    public void user_clicks_on_save() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("{int} Person(s) Of Contact is/are saved in database")
    public void persons_of_contact_are_saved_in_database(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("{int} Address(es) is/are saved in database")
    public void addresses_are_saved_in_database(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("{int} Prospect(s) is/are saved in database")
    public void prospects_are_saved_in_database(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }


    @Then("As per database Tax ID of Prospect {string} is {string}")
    public void as_per_database_tax_id_of_prospect_is(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("As per database Principal Activity of Prospect {string} is {string}")
    public void as_per_database_principal_activity_of_prospect_is(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("As per database {string} is a Person of Contact for Prospect {string}")
    public void as_per_database_is_a_person_of_contact_for_prospect(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("As per database {string} is Address for Prospect {string}")
    public void as_per_database_is_address_for_prospect(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("As per database {string} is not a Person of Contact for Prospect {string}")
    public void as_per_database_is_not_a_person_of_contact_for_prospect(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("As per database {string} is not Address for Prospect {string}")
    public void as_per_database_is_not_address_for_prospect(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
