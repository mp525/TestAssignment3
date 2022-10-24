package unit.servicelayer.booking;

import datalayer.booking.BookingStorage;
import dto.Booking;
import dto.BookingCreation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import servicelayer.booking.BookingService;
import servicelayer.booking.BookingServiceException;
import servicelayer.booking.BookingServiceImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
class BookingServiceImplUnitTest {

    private BookingService bookingService;
    private BookingStorage storageMock;

    @BeforeAll
    public void setup() {
        storageMock = mock(BookingStorage.class);
        bookingService = new BookingServiceImpl(storageMock);
    }

    @Test
    void mustCallStorageOnceWhenCreatingBooking() throws SQLException, BookingServiceException {
        bookingService.createBooking(
                1, 1, new Date(System.currentTimeMillis()),
                new Time(12, 0, 0), new Time(13, 0, 0));
        Mockito.verify(storageMock, times(1))
                .createBooking(
                        argThat(x -> (x.getCustomerId() == 1) &&
                                (x.getEmployeeId() == 1) ));
        Mockito.verifyNoMoreInteractions(storageMock);
    }

    @Test
    void mustCallStorageOnceWhenGetBookingsByCustomerId() throws SQLException, BookingServiceException {
        int id = 1;
        List<Booking> bookings = new ArrayList<>();
        Mockito.when(storageMock.getBookingsForCustomer(id)).thenReturn(bookings);

        List<Booking> retBookings = bookingService.getBookingsForCustomer(id);
        assertEquals(bookings, retBookings);
        Mockito.verify(storageMock).getBookingsForCustomer(id);
        Mockito.verifyNoMoreInteractions(storageMock);
    }

    @Test
    void getBookingsForEmployee() throws SQLException, BookingServiceException {
        int id = 1;
        List<Booking> bookings = new ArrayList<>();
        Mockito.when(storageMock.getBookingsForEmployee(id)).thenReturn(bookings);

        List<Booking> retBookings = bookingService.getBookingsForEmployee(id);
        assertEquals(bookings, retBookings);
        Mockito.verify(storageMock).getBookingsForEmployee(id);
        Mockito.verifyNoMoreInteractions(storageMock);
    }
}