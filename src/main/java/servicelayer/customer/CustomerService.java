package servicelayer.customer;

import dto.Customer;

import java.sql.SQLException;
import java.util.Collection;
import java.sql.Date;

public interface CustomerService {
    int createCustomer(String firstName, String lastName, Date birthdate) throws CustomerServiceException;
    Customer getCustomerById(int id) throws CustomerServiceException;
}
