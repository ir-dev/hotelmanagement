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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        OrganizationDTO that = (OrganizationDTO) o;
        return Objects.equals(this.id, that.id) && Objects.equals(this.name, that.name) && Objects.equals(this.address, that.address) && Objects.equals(this.eMail, that.eMail) && Objects.equals(this.paymentInformation, that.paymentInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.address, this.eMail, this.paymentInformation);
    }
}
