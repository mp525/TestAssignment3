package integration.datalayer.booking;

import com.github.javafaker.Faker;
import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.*;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.booking.BookingServiceException;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class BookingStorageImplTest extends ContainerizedDbIntegrationTest {

    private BookingStorage bookingStorage;
    private CustomerStorage customerStorage;
    private EmployeeStorage employeeStorage;

    @BeforeAll
    public void Setup() throws SQLException {
        runMigration(4);
        bookingStorage = new BookingStorageImpl(getConnectionString(), "root", getDbPassword());
        customerStorage = new CustomerStorageImpl(getConnectionString(), "root", getDbPassword());
        employeeStorage = new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword());

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
    void shouldCreateABookingInTheDatabase() throws SQLException {
        BookingCreation bookingCreation = new BookingCreation(
                1, 1, new Date(System.currentTimeMillis()),
                new Time(12, 0, 0), new Time(13, 0, 0));
        int bookingId = bookingStorage.createBooking(bookingCreation);
        assertTrue(bookingId != 0);
    }

    @Test
    void shouldGetAllBookingsForAGivenCustomer() throws SQLException {
        BookingCreation bookingCreation = new BookingCreation(
                1, 1, new Date(System.currentTimeMillis()),
                new Time(12, 0, 0), new Time(13, 0, 0));
        bookingStorage.createBooking(bookingCreation);
        List<Booking> bookings = bookingStorage.getBookingsForCustomer(1);
        assertTrue(!bookings.isEmpty());
    }

    @Test
    void shouldGetAllBookingsForAGivenEmployee() throws SQLException {
        BookingCreation bookingCreation = new BookingCreation(
                1, 1, new Date(System.currentTimeMillis()),
                new Time(12, 0, 0), new Time(13, 0, 0));
        bookingStorage.createBooking(bookingCreation);
        List<Booking> bookings = bookingStorage.getBookingsForEmployee(1);
        assertTrue(!bookings.isEmpty());
    }


}