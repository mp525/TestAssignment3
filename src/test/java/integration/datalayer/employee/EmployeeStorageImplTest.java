package integration.datalayer.employee;

import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.Employee;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class EmployeeStorageImplTest extends ContainerizedDbIntegrationTest {

    private EmployeeStorage employeeStorage;

    @BeforeAll
    public void Setup() throws SQLException {
        runMigration(4);
        employeeStorage = new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword());
    }

    @Test
    void shouldGetCorrectEmployeeFromEmployeeId() throws SQLException {
        Date birthDate = new Date(System.currentTimeMillis());
        EmployeeCreation employeeToCreate = new EmployeeCreation(
                "firstName", "lastName", birthDate);
        int returnId = employeeStorage.createEmployee(employeeToCreate);
        Employee employee = employeeStorage.getEmployeeWithId(returnId);
        assertEquals("firstName", employee.getFirstName());
        assertEquals("lastName", employee.getLastName());
        assertEquals(returnId, employee.getId());
    }

    @Test
    void shouldCreateEmployeeInDatabase() throws SQLException {
        EmployeeCreation employee = new EmployeeCreation(
                "firstName", "lastName", new Date(System.currentTimeMillis()));
        int returnId = employeeStorage.createEmployee(employee);
        assertTrue(returnId != 0);
    }
}