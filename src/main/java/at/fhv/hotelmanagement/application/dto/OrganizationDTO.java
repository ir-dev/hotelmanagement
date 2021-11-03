package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.Address;
import at.fhv.hotelmanagement.domain.model.PaymentInformation;

import java.util.List;
import java.util.Objects;

public class OrganizationDTO {
    private String id;
    private String name;
    private Address address;
    private String eMail;
    private List<PaymentInformation> paymentInformation;

    public static OrganizationDTO.Builder builder() {
        return new OrganizationDTO.Builder();
    }

    public String guestId() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public Address address() {
        return this.address;
    }

    public String eMail() {
        return this.eMail;
    }

    public List<PaymentInformation> paymentInformation() {
        return this.paymentInformation;
    }

    private OrganizationDTO() {
    }

    public static class Builder {
        private OrganizationDTO instance;

        private Builder() {
            this.instance = new OrganizationDTO();
        }

        public OrganizationDTO.Builder withId(String id) {
            this.instance.id = id;
            return this;
        }

        public OrganizationDTO.Builder withName(String name) {
            this.instance.name = name;
            return this;
        }

        public OrganizationDTO.Builder withAddress(Address address) {
            this.instance.address = address;
            return this;
        }

        public OrganizationDTO.Builder withEmail(String eMail) {
            this.instance.eMail = eMail;
            return this;
        }

        public OrganizationDTO.Builder withPaymentInformation(List<PaymentInformation> paymentInformation) {
            this.instance.paymentInformation = paymentInformation;
            return this;
        }

        public OrganizationDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in OrganizationDTO");
            Objects.requireNonNull(this.instance.name, "name must be set in OrganizationDTO");
            Objects.requireNonNull(this.instance.address, "address must be set in OrganizationDTO");
            Objects.requireNonNull(this.instance.eMail, "email must be set in OrganizationDTO");
            Objects.requireNonNull(this.instance.paymentInformation, "paymentInformation must be set in OrganizationDTO");

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
        OrganizationDTO other = (OrganizationDTO) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
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
