package servicelayer.booking;

import dto.Booking;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public interface BookingService {
    int createBooking(int customerId, int employeeId, Date date, Time start, Time end) throws BookingServiceException;
    List<Booking> getBookingsForCustomer(int customerId) throws BookingServiceException;
    List<Booking> getBookingsForEmployee(int employeeId) throws BookingServiceException;
}
