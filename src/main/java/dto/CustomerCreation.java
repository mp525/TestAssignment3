package dto;

import java.sql.Date;

public class CustomerCreation {
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

    public final String firstname, lastname;
    public Date birthdate;
    public String phoneNr;

    public CustomerCreation(String firstname, String lastname, Date birthdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
    }

    public CustomerCreation(String firstname, String lastname, Date birthdate, String phoneNr) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.phoneNr = phoneNr;
    }
}
