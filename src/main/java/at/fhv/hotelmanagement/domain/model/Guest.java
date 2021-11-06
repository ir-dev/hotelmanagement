package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.enums.Gender;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Guest {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Address address;
    private String nationality;
    private String eMail;
    private Gender gender;
    private List<PaymentInformation> paymentInformation;


    public Guest(String id, String firstName, String lastName, LocalDate birthday, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.address = address;
    }

    public String id() {
        return this.id;
    }

    public String firstName() {
        return this.firstName;
    }

    public String lastName() {
        return this.lastName;
    }

    public LocalDate birthday() {
        return this.birthday;
    }

    public Address address() {
        return this.address;
    }

    public String nationality() {
        return this.nationality;
    }

    public String email() {
        return this.eMail;
    }

    public Gender gender() {
        return this.gender;
    }

    public List<PaymentInformation> paymentInformation() {
        return this.paymentInformation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Guest guest = (Guest) o;
        return Objects.equals(this.id, guest.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
