package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.Address;
import at.fhv.hotelmanagement.domain.model.Guest;
import at.fhv.hotelmanagement.domain.model.GuestId;
import at.fhv.hotelmanagement.domain.model.Organization;
import at.fhv.hotelmanagement.domain.model.enums.Salutation;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public final class GuestDTO {
    private GuestId id;
    private Optional<Organization> organization;
    private Salutation salutation;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Address address;
    private String specialNotes;

    public static GuestDTO.Builder builder() {
        return new GuestDTO.Builder();
    }

    public GuestId id() {
        return this.id;
    }

    public Optional<Organization> organization() {
        return this.organization;
    }

    public Salutation salutation() {
        return this.salutation;
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

    public String specialNotes() {
        return this.specialNotes;
    }

    private GuestDTO() {
    }

    public static class Builder {
        private GuestDTO instance;

        private Builder() {
            this.instance = new GuestDTO();
        }

        public Builder withGuestEntity(Guest guest) {
            this.instance.id = guest.getGuestId();
            this.instance.organization = guest.getOrganization();
            this.instance.salutation = guest.getSalutation();
            this.instance.firstName = guest.getFirstName();
            this.instance.lastName = guest.getLastName();
            this.instance.birthday = guest.getBirthday();
            this.instance.address = guest.getAddress();
            this.instance.specialNotes = guest.getSpecialNotes();
            return this;
        }

        public GuestDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in GuestDTO");

            return this.instance;
        }


    }
}
