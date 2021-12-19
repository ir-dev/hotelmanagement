package at.fhv.hotelmanagement.domain.model.guest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class Guest {
    // generated hibernate id
    private Long id;
    private GuestId guestId;
    // organization may be null
    private Organization organization;
    private Salutation salutation;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Address address;
    private String specialNotes;

    // required for hibernate
    private Guest() {
    }

    Guest(GuestId guestId, Organization organization, String salutation, String firstName, String lastName, LocalDate dateOfBirth, Address address, String specialNotes) {
        this.guestId = guestId;
        this.organization = organization;
        this.salutation = Salutation.valueOf(salutation);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.specialNotes = specialNotes;
    }

    public GuestId getGuestId() {
        return this.guestId;
    }

    public Optional<Organization> getOrganization() {
        return Optional.ofNullable(this.organization);
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

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Address getAddress() {
        return this.address;
    }

    public String getSpecialNotes() {
        return this.specialNotes;
    }

    public Optional<BigDecimal> getDiscountRate() {
        if (this.organization != null) {
            return Optional.of(this.organization.getDiscountRate());
        } else {
            return Optional.of(BigDecimal.valueOf(0));
        }
    }
}
