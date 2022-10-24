package servicelayer.booking;

import datalayer.booking.BookingStorage;
import datalayer.customer.CustomerStorage;
import dto.Booking;
import dto.BookingCreation;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class BookingServiceImpl implements BookingService {

    private BookingStorage bookingStorage;

    public BookingServiceImpl(BookingStorage bookingStorage) {
        this.bookingStorage = bookingStorage;
    }

    @Override
    public int createBooking(int customerId, int employeeId, Date date, Time start, Time end) throws BookingServiceException {
        bookingParamsValid(customerId, employeeId, date, start, end);
        try {
            int returnId = bookingStorage.createBooking(new BookingCreation(customerId, employeeId, date, start, end));
            // send sms to customers phoneNr (handled elsewhere)
            return returnId;
        } catch (SQLException throwables) {
            throw new BookingServiceException(throwables.getMessage());
        }
    }

    @Override
    public List<Booking> getBookingsForCustomer(int customerId) throws BookingServiceException {
        if(customerId == 0) {
            throw new BookingServiceException("Given customer id must be valid.");
        }
        try {
            return bookingStorage.getBookingsForCustomer(customerId);
        } catch (SQLException throwables) {
            throw new BookingServiceException(throwables.getMessage());
        }
    }

    @Override
    public List<Booking> getBookingsForEmployee(int employeeId) throws BookingServiceException {
        if(employeeId == 0) {
            throw new BookingServiceException("Given employee id must be valid.");
        }
        try {
            return bookingStorage.getBookingsForEmployee(employeeId);
        } catch (SQLException throwables) {
            throw new BookingServiceException(throwables.getMessage());
        }
    }

    private static void bookingParamsValid(int customerId, int employeeId, Date date, Time start, Time end) throws BookingServiceException {
        if (customerId == 0) {
            throw new BookingServiceException("Customer id must be valid.");
        }
        if (employeeId == 0) {
            throw new BookingServiceException("Employee id must be valid.");
        }
        if (date == null) {
            throw new BookingServiceException("Date must be valid.");
        }
        if (start == null || end == null){
            throw new BookingServiceException("Start and end time must be valid.");
        }
    }
}
