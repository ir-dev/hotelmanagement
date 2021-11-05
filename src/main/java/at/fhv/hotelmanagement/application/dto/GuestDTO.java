package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.Address;
import at.fhv.hotelmanagement.domain.model.Enums.Gender;
import at.fhv.hotelmanagement.domain.model.PaymentInformation;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class GuestDTO {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Address address;
    private String nationality;
    private String eMail;
    private Gender gender;
    private List<PaymentInformation> paymentInformation;

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

    public LocalDate BirthDate() {
        return this.birthDate;
    }

    public Address address() {
        return this.address;
    }

    public String nationality() {
        return this.nationality;
    }

    public String eMail() {
        return this.eMail;
    }

    public Gender gender() {
        return this.gender;
    }

    public List<PaymentInformation> paymentInformation() {
        return this.paymentInformation;
    }

    private GuestDTO() {
    }

    public static class Builder {
        private GuestDTO instance;

        private Builder() {
            this.instance = new GuestDTO();
        }

        public Builder withId(String id) {
            this.instance.id = id;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.instance.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.instance.lastName = lastName;
            return this;
        }

        public Builder withBirthDate(LocalDate birthDate) {
            this.instance.birthDate = birthDate;
            return this;
        }

        public Builder withAddress(Address address) {
            this.instance.address = address;
            return this;
        }

        public Builder withNationality(String nationality) {
            this.instance.nationality = nationality;
            return this;
        }

        public Builder withEmail(String eMail) {
            this.instance.eMail = eMail;
            return this;
        }

        public Builder withGender(Gender gender) {
            this.instance.gender = gender;
            return this;
        }

        public Builder withPaymentInformation(List<PaymentInformation> paymentInformation) {
            this.instance.paymentInformation = paymentInformation;
            return this;
        }

        public GuestDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in GuestDTO");
            Objects.requireNonNull(this.instance.firstName, "firstName must be set in GuestDTO");
            Objects.requireNonNull(this.instance.lastName, "lastName must be set in GuestDTO");
            Objects.requireNonNull(this.instance.birthDate, "birthdate must be set in GuestDTO");
            Objects.requireNonNull(this.instance.address, "address must be set in GuestDTO");
            Objects.requireNonNull(this.instance.nationality, "nationality must be set in GuestDTO");
            Objects.requireNonNull(this.instance.eMail, "email must be set in GuestDTO");
            Objects.requireNonNull(this.instance.gender, "gender must be set in GuestDTO");
            Objects.requireNonNull(this.instance.paymentInformation, "paymentInformation must be set in GuestDTO");

            return this.instance;
        }


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        GuestDTO guestDTO = (GuestDTO) o;
        return Objects.equals(this.id, guestDTO.id) && Objects.equals(this.firstName, guestDTO.firstName) && Objects.equals(this.lastName, guestDTO.lastName) && Objects.equals(this.birthDate, guestDTO.birthDate) && Objects.equals(this.address, guestDTO.address) && Objects.equals(this.nationality, guestDTO.nationality) && Objects.equals(this.eMail, guestDTO.eMail) && this.gender == guestDTO.gender && Objects.equals(this.paymentInformation, guestDTO.paymentInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.lastName, this.birthDate, this.address, this.nationality, this.eMail, this.gender, this.paymentInformation);
    }
}
