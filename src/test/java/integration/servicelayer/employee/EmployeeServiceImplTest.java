package integration.servicelayer.employee;

import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.Employee;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.booking.BookingServiceException;
import servicelayer.employee.EmployeeService;
import servicelayer.employee.EmployeeServiceException;
import servicelayer.employee.EmployeeServiceImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class EmployeeServiceImplTest extends ContainerizedDbIntegrationTest {

    EmployeeService employeeService;
    EmployeeStorage employeeStorage;

    @BeforeAll
    public void setup() throws SQLException {
        runMigration(4);
        employeeStorage = new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword());
        employeeService = new EmployeeServiceImpl(employeeStorage);
        EmployeeCreation employeeCreation = new EmployeeCreation(
                "firstName", "lastName", new Date(System.currentTimeMillis()));
        employeeStorage.createEmployee(employeeCreation);
    }

    @Test
    void shouldCreateEmployeeInDatabaseFromService() throws EmployeeServiceException {
        EmployeeCreation employeeCreation = new EmployeeCreation(
                "firstNameTest", "lastNameTest", new Date(System.currentTimeMillis()));
        int employeeId = employeeService.createEmployee(employeeCreation);
        assertTrue(employeeId != 0);
    }

    @Test
    void createEmployeeThrowsCorrectException() {
        EmployeeCreation employeeCreation = new EmployeeCreation(
                "", "lastNameTest", new Date(System.currentTimeMillis()));
        Exception exception = assertThrows(EmployeeServiceException.class, () -> {
            employeeService.createEmployee(employeeCreation);
        });
        String expectedMessage = "First name must not be empty.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldGetEmployeeByIdFromService() throws EmployeeServiceException {
        int id = 1;
        Employee employee = employeeService.getEmployeeById(id);
        assertEquals("firstName", employee.getFirstName());
        assertEquals("lastName", employee.getLastName());
        assertEquals(id, employee.getId());
    }

    @Test
    void getEmployeeByIdThrowsCorrectException() {
        Exception exception = assertThrows(EmployeeServiceException.class, () -> {
            employeeService.getEmployeeById(0);
        });
        String expectedMessage = "Given id must be valid.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}