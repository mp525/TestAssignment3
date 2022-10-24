package unit.servicelayer.employee;

import datalayer.employee.EmployeeStorage;
import dto.Employee;
import dto.EmployeeCreation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import servicelayer.employee.EmployeeService;
import servicelayer.employee.EmployeeServiceException;
import servicelayer.employee.EmployeeServiceImpl;

import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
class EmployeeServiceImplUnitTest {

    private EmployeeService employeeService;
    private EmployeeStorage storageMock;

    @BeforeAll
    public void setup() {
        storageMock = mock(EmployeeStorage.class);
        employeeService = new EmployeeServiceImpl(storageMock);
    }

    @Test
    void mustCallStorageMethodWhenCreatingEmployee() throws SQLException, EmployeeServiceException {
        EmployeeCreation employeeCreation = new EmployeeCreation(
                "firstName", "lastName", new Date(System.currentTimeMillis()));
        employeeService.createEmployee(employeeCreation);

        // Verify that storageMock was called 1 times with the method "createEmployee"
        // and the argument had firstName and lastName
        Mockito.verify(storageMock, times(1))
                .createEmployee(
                        argThat(x -> x.getFirstName().equals("firstName") &&
                                x.getLastName().equals("lastName")));
    }

    @Test
    void assertOnlyGetEmployeeWithIdWasCalled() throws SQLException, EmployeeServiceException {
        // There are multiple ways of testing with Mockito. I've tried to use different ones.
        int id = 1;
        Employee emp = new Employee(id, "firstName", "lastName", new Date(System.currentTimeMillis()));
        Mockito.when(storageMock.getEmployeeWithId(id)).thenReturn(emp);

        Employee retEmp = employeeService.getEmployeeById(id);
        assertTrue(retEmp != null);
        Mockito.verify(storageMock).getEmployeeWithId(id);
        Mockito.verifyNoMoreInteractions(storageMock);
    }

}