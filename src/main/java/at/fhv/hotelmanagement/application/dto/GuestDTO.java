package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.Address;
import at.fhv.hotelmanagement.domain.model.Guest;
import at.fhv.hotelmanagement.domain.model.enums.Gender;
import at.fhv.hotelmanagement.domain.model.PaymentInformation;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class GuestDTO {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Address address;
    private String nationality;
    private String email;
    private Gender gender;

    public static GuestDTO.Builder builder() {
        return new GuestDTO.Builder();
    }

    public String guestId() {
        return this.id;
    }

    public String firstName() {
        return this.firstName;
    }

    public String lastName() {
        return this.lastName;
    }

    public String fullName() {
        return this.firstName + this.lastName;
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
        return this.email;
    }

    public Gender gender() {
        return this.gender;
    }

    private GuestDTO() {
    }

    public static class Builder {
        private GuestDTO instance;

        private Builder() {
            this.instance = new GuestDTO();
        }

        public Builder withGuestEntity(Guest guest) {
            this.instance.id = guest.id();
            this.instance.firstName = guest.firstName();
            this.instance.lastName = guest.lastName();
            this.instance.birthday = guest.birthday();
            this.instance.address = guest.address();
            this.instance.nationality = guest.nationality();
            this.instance.email = guest.email();
            this.instance.gender = guest.gender();
            return this;
        }

        public GuestDTO build() {
//            Objects.requireNonNull(this.instance.id, "id must be set in GuestDTO");
//            Objects.requireNonNull(this.instance.firstName, "firstName must be set in GuestDTO");
//            Objects.requireNonNull(this.instance.lastName, "lastName must be set in GuestDTO");
//            Objects.requireNonNull(this.instance.birthDate, "birthdate must be set in GuestDTO");
//            Objects.requireNonNull(this.instance.address, "address must be set in GuestDTO");
//            Objects.requireNonNull(this.instance.nationality, "nationality must be set in GuestDTO");
//            Objects.requireNonNull(this.instance.email, "email must be set in GuestDTO");
//            Objects.requireNonNull(this.instance.gender, "gender must be set in GuestDTO");
            return this.instance;
        }


    }


}
