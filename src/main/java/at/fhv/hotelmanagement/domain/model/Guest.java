package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.enums.Salutation;

import java.time.LocalDate;
import java.util.Optional;

public class Guest {
    // generated hibernate id
    private Long id;
    private GuestId guestId;
    private Optional<Organization> organization;
    private Salutation salutation;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Address address;
    private String specialNotes;

    public Guest(GuestId guestId, Optional<Organization> organization, String salutation, String firstName, String lastName, LocalDate birthday, Address address, String specialNotes) {
        this.guestId = guestId;
        this.organization = organization;
        this.salutation = Salutation.valueOf(salutation);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.address = address;
        this.specialNotes = specialNotes;
    }

    public GuestId getGuestId() {
        return this.guestId;
    }

    public Optional<Organization> getOrganization() {
        return this.organization;
    }

    public Salutation getSalutation() {
        return this.salutation;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public Address getAddress() {
        return this.address;
    }

    public String getSpecialNotes() {
        return this.specialNotes;
    }
}
