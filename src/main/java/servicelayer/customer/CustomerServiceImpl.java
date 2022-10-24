package servicelayer.customer;

import datalayer.customer.CustomerStorage;
import dto.Customer;
import dto.CustomerCreation;

import java.sql.SQLException;
import java.util.Collection;
import java.sql.Date;

public class CustomerServiceImpl implements CustomerService {

    private CustomerStorage customerStorage;

    public CustomerServiceImpl(CustomerStorage customerStorage) {
        this.customerStorage = customerStorage;
    }

    @Override
    public int createCustomer(String firstName, String lastName, Date birthdate) throws CustomerServiceException {
        if(firstName == null || firstName == ""){
            throw new CustomerServiceException("First name must not be empty.");
        }
        try {
            return customerStorage.createCustomer(new CustomerCreation(firstName, lastName, birthdate));
        } catch (SQLException throwables) {
            throw new CustomerServiceException(throwables.getMessage());
        }
    }

    @Override
    public Customer getCustomerById(int id) throws CustomerServiceException {
        if(id == 0){
            throw new CustomerServiceException("Given id must be valid.");
        }
        try {
            return customerStorage.getCustomerWithId(id);
        } catch (SQLException throwables) {
            throw new CustomerServiceException(throwables.getMessage());
        }
    }

}
