package main;

import datalayer.booking.BookingStorageImpl;
import datalayer.employee.EmployeeStorageImpl;
import dto.*;
import datalayer.customer.CustomerStorageImpl;

import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;

public class Main {

    private static final String conStr = "jdbc:mysql://localhost:3307/Booking";
    private static final String user = "root";
    private static final String pass = "testuser123";

    public static void main(String[] args) throws SQLException {
        CustomerStorageImpl storage = new CustomerStorageImpl(conStr, user, pass);
        EmployeeStorageImpl storageEmp = new EmployeeStorageImpl(conStr, user, pass);
        BookingStorageImpl storageBook = new BookingStorageImpl(conStr, user, pass);
        //employeeAdd(storageEmp);
        customerAdd(storage, "test2", "test2", "88888888");
        //bookingAdd(storageBook);
    }

    private static void bookingAdd(BookingStorageImpl storageBook) throws SQLException {
        BookingCreation booking = new BookingCreation(
                6, 1, new Date(System.currentTimeMillis()),
                new Time(13, 30, 0),
                new Time(14, 0, 0));
        int id = storageBook.createBooking(booking);

        System.out.println("Got bookings: ");
        for(Booking b : storageBook.getBookingsForCustomer(6)) {
            System.out.println("bookings with customerId: " + b.getCustomerId());
        }
        System.out.println("The end.");
    }

    private static void employeeAdd(EmployeeStorageImpl storageEmp) throws SQLException {
        EmployeeCreation emp = new EmployeeCreation("empFirstname", "empLastname", new Date(System.currentTimeMillis()));
        int id = storageEmp.createEmployee(emp);

        System.out.println("Got employee: ");
        System.out.println(storageEmp.getEmployeeWithId(id).getFirstName());
        System.out.println("The end.");
    }

    private static void customerAdd(CustomerStorageImpl storage, String fName, String lName, String phoneNr) throws SQLException {
        CustomerCreation customerCR = new CustomerCreation(fName, lName, new Date(System.currentTimeMillis()), phoneNr);
        int id = storage.createCustomer(customerCR);

        System.out.println("Got customers: ");
        for(Customer c : storage.getCustomers()) {
            System.out.println(toString(c));
        }
        System.out.println("The end.");
    }

    public static String toString(Customer c) {
        return "{" + c.getId() + ", " + c.getFirstname() + ", " + c.getLastname() + ", " + c.getBirthdate() + "}";
    }
}
