package datalayer.employee;

import dto.Customer;
import dto.Employee;
import dto.EmployeeCreation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class EmployeeStorageImpl implements EmployeeStorage{

    private String connectionString;
    private String username, password;

    public EmployeeStorageImpl(String conStr, String user, String pass){
        connectionString = conStr;
        username = user;
        password = pass;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }

    @Override
    public Employee getEmployeeWithId(int employeeId) throws SQLException {
        var sql = "select ID, firstname, lastname, birthdate from Employees where id = ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);

            try (var resultSet = stmt.executeQuery()) {
                if (resultSet.next()){
                    var id = resultSet.getInt("ID");
                    var firstname = resultSet.getString("firstname");
                    var lastname = resultSet.getString("lastname");
                    var birthdate = resultSet.getDate("birthdate");
                    return new Employee(id, firstname, lastname, birthdate);
                }
                return null;
            }
        }
    }

    @Override
    public int createEmployee(EmployeeCreation employee) throws SQLException {
        var sql = "insert into Employees(firstname, lastname, birthdate) values (?, ?, ?)";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setDate(3, employee.getBirthDate());

            stmt.executeUpdate();

            // get the newly created id
            try (var resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                int newId = resultSet.getInt(1);
                return newId;
            }
        }
    }
}
