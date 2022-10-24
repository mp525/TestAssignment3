package servicelayer.employee;

import datalayer.employee.EmployeeStorage;
import dto.Employee;
import dto.EmployeeCreation;

import java.sql.SQLException;

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeStorage employeeStorage;

    public EmployeeServiceImpl(EmployeeStorage employeeStorage) {
        this.employeeStorage = employeeStorage;
    }

    @Override
    public int createEmployee(EmployeeCreation employee) throws EmployeeServiceException {
        firstNameLastNameValid(employee);
        try {
            return employeeStorage.createEmployee(employee);
        } catch (SQLException throwables) {
            throw new EmployeeServiceException(throwables.getMessage());
        }
    }

    @Override
    public Employee getEmployeeById(int employeeId) throws EmployeeServiceException {
        if(employeeId == 0) {
            throw new EmployeeServiceException("Given id must be valid.");
        }
        try {
            return employeeStorage.getEmployeeWithId(employeeId);
        } catch (SQLException throwables) {
            throw new EmployeeServiceException(throwables.getMessage());
        }
    }

    private static void firstNameLastNameValid(EmployeeCreation employee) throws EmployeeServiceException {
        if(employee.getFirstName() == null || employee.getFirstName() == ""){
            throw new EmployeeServiceException("First name must not be empty.");
        }
        if(employee.getLastName() == null || employee.getLastName() == ""){
            throw new EmployeeServiceException("Last name must not be empty.");
        }
    }
}
