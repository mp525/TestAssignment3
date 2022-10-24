package integration.servicelayer.booking;

import com.github.javafaker.Faker;
import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.Booking;
import dto.BookingCreation;
import dto.CustomerCreation;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.booking.BookingService;
import servicelayer.booking.BookingServiceException;
import servicelayer.booking.BookingServiceImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class BookingServiceImplTest extends ContainerizedDbIntegrationTest {
    private BookingStorage bookingStorage;
    private CustomerStorage customerStorage;
    private EmployeeStorage employeeStorage;
    private BookingService bookingService;

    @BeforeAll
    public void setup() throws SQLException {
        runMigration(4);
        bookingStorage = new BookingStorageImpl(getConnectionString(), "root", getDbPassword());
        customerStorage = new CustomerStorageImpl(getConnectionString(), "root", getDbPassword());
        employeeStorage = new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword());
        bookingService = new BookingServiceImpl(bookingStorage);

        addFakeCustomers(1);
        addFakeEmployees(1);
    }

    private void addFakeCustomers(int numCustomers) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numCustomers; i++) {
            CustomerCreation c = new CustomerCreation(faker.name().firstName(), faker.name().lastName(), new Date(System.currentTimeMillis()), faker.phoneNumber().phoneNumber());
            customerStorage.createCustomer(c);
        }
    }
    private void addFakeEmployees(int numEmployees) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numEmployees; i++) {
            EmployeeCreation e = new EmployeeCreation(faker.name().firstName(), faker.name().lastName(), new Date(System.currentTimeMillis()));
            employeeStorage.createEmployee(e);
        }
    }

    @Test
    void shouldCreateABookingInDatabaseWithService() throws BookingServiceException {
        int bookingId = bookingService.createBooking(1, 1, new Date(System.currentTimeMillis()),
                new Time(12, 0, 0), new Time(13, 0, 0));
        assertTrue(bookingId != 0);
    }

    @Test
    void createBookingThrowsCorrectException() {
        Exception exception = assertThrows(BookingServiceException.class, () -> {
            bookingService.createBooking(0, 1, new Date(System.currentTimeMillis()),
                new Time(12, 0, 0), new Time(13, 0, 0));
            });
        String expectedMessage = "Customer id must be valid.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldGetBookingsOfCustomerWithService() throws BookingServiceException {
        int bookingId = bookingService.createBooking(1, 1, new Date(System.currentTimeMillis()),
                new Time(12, 0, 0), new Time(13, 0, 0));
        List<Booking> bookings = bookingService.getBookingsForCustomer(1);
        assertTrue(!bookings.isEmpty());
    }
    @Test
    void getBookingByCustomerThrowsCorrectException() throws BookingServiceException {
        int bookingId = bookingService.createBooking(1, 1, new Date(System.currentTimeMillis()),
                new Time(12, 0, 0), new Time(13, 0, 0));
        Exception exception = assertThrows(BookingServiceException.class, () -> {
            bookingService.getBookingsForCustomer(0);
        });
        String expectedMessage = "Given customer id must be valid.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldGetBookingsOfEmployeeWithService() throws BookingServiceException {
        int bookingId = bookingService.createBooking(1, 1, new Date(System.currentTimeMillis()),
                new Time(12, 0, 0), new Time(13, 0, 0));
        List<Booking> bookings = bookingService.getBookingsForEmployee(1);
        assertTrue(!bookings.isEmpty());
    }

    @Test
    void getBookingByEmployeeThrowsCorrectException() throws BookingServiceException {
        int bookingId = bookingService.createBooking(1, 1, new Date(System.currentTimeMillis()),
                new Time(12, 0, 0), new Time(13, 0, 0));
        Exception exception = assertThrows(BookingServiceException.class, () -> {
            bookingService.getBookingsForEmployee(0);
        });
        String expectedMessage = "Given employee id must be valid.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}