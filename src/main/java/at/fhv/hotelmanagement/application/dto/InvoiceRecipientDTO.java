package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.stay.InvoiceRecipient;
import java.util.Objects;

public class InvoiceRecipientDTO {
    private String firstName;
    private String lastName;
    private String addressStreet;
    private String addressZipcode;
    private String addressCity;
    private String addressCountry;

    public static InvoiceRecipientDTO.Builder builder() {return new InvoiceRecipientDTO.Builder();}

    public String firstName() {return this.firstName;}

    public String lastName() {return this.lastName;}

    public String addressStreet() {return this.addressStreet;}

    public String addressZipcode() {return this.addressZipcode;}

    public String addressCity() {return this.addressCity;}

    public String addressCountry() {return this.addressCountry;}

    private InvoiceRecipientDTO() {}

    public static class Builder {
        private InvoiceRecipientDTO instance;

        private Builder() {this.instance = new InvoiceRecipientDTO();}

        public Builder withInvoiceRecipientEntity(InvoiceRecipient invoiceRecipient) {
            this.instance.firstName = invoiceRecipient.getFirstName();
            this.instance.lastName = invoiceRecipient.getLastName();
            this.instance.addressStreet = invoiceRecipient.getAddress().getStreet();
            this.instance.addressZipcode = invoiceRecipient.getAddress().getZipcode();
            this.instance.addressCity = invoiceRecipient.getAddress().getCity();
            this.instance.addressCountry = String.valueOf(invoiceRecipient.getAddress().getCountry());
            return this;
        }

        public InvoiceRecipientDTO build() {
            Objects.requireNonNull(this.instance.firstName, "firstname must be set in InvoiceRecipientDTO");
            Objects.requireNonNull(this.instance.lastName, "lastname must be set in InvoiceRecipientDTO");
            Objects.requireNonNull(this.instance.addressStreet, "addressStreet must be set in InvoiceRecipientDTO");
            Objects.requireNonNull(this.instance.addressZipcode, "addressZipcode must be set in InvoiceRecipientDTO");
            Objects.requireNonNull(this.instance.addressCity, "addressCity must be set in InvoiceRecipientDTO");
            Objects.requireNonNull(this.instance.addressCountry, "addressCountry must be set in InvoiceRecipientDTO");

            return this.instance;
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InvoiceRecipientDTO invoiceRecipientDTO = (InvoiceRecipientDTO) o;
        return  Objects.equals(this.firstName, invoiceRecipientDTO.firstName) && Objects.equals(this.lastName, invoiceRecipientDTO.lastName) && Objects.equals(this.addressStreet, invoiceRecipientDTO.addressStreet) && Objects.equals(this.addressZipcode, invoiceRecipientDTO.addressZipcode) && Objects.equals(this.addressCity, invoiceRecipientDTO.addressCity) && Objects.equals(this.addressCountry, invoiceRecipientDTO.addressCountry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.firstName, this.lastName, this.addressStreet, this.addressZipcode, this.addressCity, this.addressCountry);
    }

}
