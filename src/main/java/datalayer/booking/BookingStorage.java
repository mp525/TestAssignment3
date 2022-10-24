package datalayer.booking;

import dto.Booking;
import dto.BookingCreation;

import java.sql.SQLException;
import java.util.List;

public interface BookingStorage {
    public int createBooking(BookingCreation booking) throws SQLException;
    public List<Booking> getBookingsForCustomer(int customerId) throws SQLException;
    public List<Booking> getBookingsForEmployee(int employeeId) throws SQLException;
}
