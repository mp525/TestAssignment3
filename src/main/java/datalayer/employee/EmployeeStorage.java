package datalayer.employee;

import dto.Booking;
import dto.Employee;
import dto.EmployeeCreation;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeStorage {
    public int createEmployee(EmployeeCreation employee) throws SQLException;
    public Employee getEmployeeWithId(int employeeId) throws SQLException;
}
