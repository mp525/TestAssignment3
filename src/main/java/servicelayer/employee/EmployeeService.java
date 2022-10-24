package servicelayer.employee;

import dto.Employee;
import dto.EmployeeCreation;

import java.sql.SQLException;

public interface EmployeeService {
    int createEmployee(EmployeeCreation employee) throws EmployeeServiceException;
    Employee getEmployeeById(int employeeId) throws EmployeeServiceException;
}
