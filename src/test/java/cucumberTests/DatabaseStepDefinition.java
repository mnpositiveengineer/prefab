package cucumberTests;

import com.prefab.sales.client.repositories.AddressRepository;
import com.prefab.sales.database.DbManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseStepDefinition {

    @Then("{int} Person(s) Of Contact is/are saved in database")
    public void persons_of_contact_are_saved_in_database(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("{short} Address(es) is/are saved in database")
    public void addresses_are_saved_in_database(short amount) {
        try {
            DbManager dbManager = DbManager.getInstance();
            Statement statement = dbManager.getConnection().createStatement();
            int number_of_addresses = statement.executeQuery("SELECT * FROM prefab_sales.addresses").getRow();
            Assertions.assertEquals(amount, number_of_addresses);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
