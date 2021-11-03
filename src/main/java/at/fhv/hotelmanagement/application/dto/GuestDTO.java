package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.Address;
import at.fhv.hotelmanagement.domain.model.Gender;
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        GuestDTO other = (GuestDTO) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(other.firstName)) {
            return false;
        }
        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }

        if (birthDate == null) {
            if (other.birthDate != null) {
                return false;
            }
        } else if (!birthDate.equals(other.birthDate)) {
            return false;
        }

        if (address == null) {
            if (other.address != null) {
                return false;
            }
        } else if (!address.equals(other.address)) {
            return false;
        }

        if (eMail == null) {
            if (other.eMail != null) {
                return false;
            }
        } else if (!eMail.equals(other.eMail)) {
            return false;
        }

        return true;
    }
}
