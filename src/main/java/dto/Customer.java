package dto;

import java.sql.Date;

public class Customer {
    private final int id;
    private final String firstname, lastname;
    private Date birthdate;
    private String phoneNr;

    public Customer(int id, String firstname, String lastname, Date birthdate, String phoneNr) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.phoneNr = phoneNr;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getPhoneNr() {
        return phoneNr;
    }
}
