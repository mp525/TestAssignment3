package integration.servicelayer.customer;

import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import dto.Customer;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.customer.CustomerService;
import servicelayer.customer.CustomerServiceException;
import servicelayer.customer.CustomerServiceImpl;

import java.sql.SQLException;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceImplTest extends ContainerizedDbIntegrationTest {

    private CustomerService svc;
    private CustomerStorage storage;

    @BeforeAll
    public void setup() {
        runMigration(4);
        storage = new CustomerStorageImpl(getConnectionString(),"root", getDbPassword());
        svc = new CustomerServiceImpl(storage);
    }

    @Test
    public void mustSaveCustomerToDatabaseWhenCallingCreateCustomer() throws CustomerServiceException, SQLException {
        // Arrange
        var firstName = "John";
        var lastName = "Johnson";
        var bday = new Date(1239821l);
        int id = svc.createCustomer(firstName, lastName, bday);

        // Act
        var createdCustomer = storage.getCustomerWithId(id);

        // Assert
        assertEquals(firstName, createdCustomer.getFirstname());
        assertEquals(lastName, createdCustomer.getLastname());
    }

    @Test
    public void createCustomerShouldThrowCorrectException() {
        var firstName = "";
        var lastName = "Johnson";
        var bday = new Date(1239821l);
        Exception exception = assertThrows(CustomerServiceException.class, () -> {
            svc.createCustomer(firstName, lastName, bday);
        });
        String expectedMessage = "First name must not be empty.";
        String actual = exception.getMessage();
        assertEquals(expectedMessage, actual);
    }

    @Test
    public void shouldGetCustomerById() throws CustomerServiceException {
        var firstName = "John";
        var lastName = "Johnson";
        var bday = new Date(1239821l);
        int expected = svc.createCustomer(firstName, lastName, bday);
        Customer customer = svc.getCustomerById(expected);

        assertEquals(expected, customer.getId());
    }

    @Test
    public void getCustomerByIdShouldThrowCorrectException() {
        Exception exception = assertThrows(CustomerServiceException.class, () -> {
            svc.getCustomerById(0);
        });
        String expectedMessage = "Given id must be valid.";
        String actual = exception.getMessage();
        assertEquals(expectedMessage, actual);
    }
}
