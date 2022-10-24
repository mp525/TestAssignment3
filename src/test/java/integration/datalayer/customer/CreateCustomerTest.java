package integration.datalayer.customer;

import com.github.javafaker.Faker;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import dto.CustomerCreation;
import integration.ContainerizedDbIntegrationTest;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class CreateCustomerTest extends ContainerizedDbIntegrationTest {
    private CustomerStorage customerStorage;

    @BeforeAll
    public void setup() throws SQLException {
        runMigration(4);
        customerStorage = new CustomerStorageImpl(getConnectionString(), "root", getDbPassword());

        var numCustomers = customerStorage.getCustomers().size();
        if (numCustomers < 100) {
            addFakeCustomers(100 - numCustomers);
        }
    }

    private void addFakeCustomers(int numCustomers) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numCustomers; i++) {
            CustomerCreation c = new CustomerCreation(faker.name().firstName(), faker.name().lastName(), new Date(System.currentTimeMillis()), faker.phoneNumber().phoneNumber());
            customerStorage.createCustomer(c);
        }

    }

    @Test
    public void mustSaveCustomerInDatabaseWhenCallingCreateCustomer() throws SQLException {
        customerStorage.createCustomer(
                new CustomerCreation("John","Carlssonn",
                        new Date(System.currentTimeMillis()), "88888888"));
        var customers = customerStorage.getCustomers();
        assertTrue(
                customers.stream().anyMatch(x ->
                        x.getFirstname().equals("John") &&
                        x.getLastname().equals("Carlssonn")));
    }

    @Test
    public void mustReturnLatestId() throws SQLException {
        var id1 = customerStorage.createCustomer(new CustomerCreation("a", "b", new Date(System.currentTimeMillis()), "88888888"));
        var id2 = customerStorage.createCustomer(new CustomerCreation("c", "d", new Date(System.currentTimeMillis()), "99999999"));
        assertEquals(1, id2 - id1);
    }
}
