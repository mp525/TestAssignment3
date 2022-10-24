package datalayer.booking;

import dto.Booking;
import dto.BookingCreation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingStorageImpl implements BookingStorage{

    private String connectionString;
    private String username, password;

    public BookingStorageImpl(String conStr, String user, String pass){
        connectionString = conStr;
        username = user;
        password = pass;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }

    @Override
    public List<Booking> getBookingsForCustomer(int customerId) throws SQLException {
        String query = "select * from Bookings where customerId = ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(query)) {
            var results = new ArrayList<Booking>();
            stmt.setInt(1, customerId);

            try (ResultSet resultSet = stmt.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    int customer = resultSet.getInt("customerId");
                    int employeeId = resultSet.getInt("employeeId");
                    Date date = resultSet.getDate("date");
                    Time start = resultSet.getTime("start");
                    Time end = resultSet.getTime("end");

                    Booking b = new Booking(id, customer, employeeId, date, start, end);
                    results.add(b);
                }
            }

            return results;
        }
    }

    @Override
    public List<Booking> getBookingsForEmployee(int employeeId) throws SQLException {
        String query = "select * from Bookings where employeeId = ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(query)) {
            var results = new ArrayList<Booking>();
            stmt.setInt(1, employeeId);

            try (ResultSet resultSet = stmt.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    int customer = resultSet.getInt("customerId");
                    int employeeID = resultSet.getInt("employeeId");
                    Date date = resultSet.getDate("date");
                    Time start = resultSet.getTime("start");
                    Time end = resultSet.getTime("end");

                    Booking b = new Booking(id, customer, employeeID, date, start, end);
                    results.add(b);
                }
            }

            return results;
        }
    }

    @Override
    public int createBooking(BookingCreation booking) throws SQLException {
        var sql = "insert into Bookings(customerId, employeeId, date, start, end) values (?, ?, ?, ?, ?)";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, booking.getCustomerId());
            stmt.setInt(2, booking.getEmployeeId());
            stmt.setDate(3, booking.getDate());
            stmt.setTime(4, booking.getStart());
            stmt.setTime(5, booking.getEnd());

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
