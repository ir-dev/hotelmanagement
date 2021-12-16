package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.guest.Guest;
import at.fhv.hotelmanagement.domain.model.guest.Organization;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public final class GuestDTO {
    private String id;
    private Boolean isOrganization;
    private String organizationName;
    private BigDecimal discountRate;
    private String salutation;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String addressStreet;
    private String addressZipcode;
    private String addressCity;
    private String addressCountry;
    private String specialNotes;

    public static GuestDTO.Builder builder() {
        return new GuestDTO.Builder();
    }

    public String id() {
        return this.id;
    }

    public Boolean isOrganization() { return this.isOrganization; }

    public String organizationName() { return this.organizationName; }

    public BigDecimal discountRate() { return this.discountRate; }

    public String salutation() {
        return this.salutation;
    }

    public String firstName() {
        return this.firstName;
    }

    public String lastName() {
        return this.lastName;
    }

    public LocalDate dateOfBirth() {
        return this.dateOfBirth;
    }

    public String addressStreet() { return this.addressStreet; }

    public String addressZipcode() { return this.addressZipcode; }

    public String addressCity() { return this.addressCity; }

    public String addressCountry() {
        return this.addressCountry;
    }

    public String specialNotes() { return this.specialNotes; }

    private GuestDTO() {
    }

    public static class Builder {
        private GuestDTO instance;

        private Builder() {
            this.instance = new GuestDTO();
        }

        public Builder withGuestEntity(Guest guest) {
            this.instance.id = guest.getGuestId().getId();
            this.instance.isOrganization = guest.getOrganization().isPresent();
            if (this.instance.isOrganization) {
                Organization organization = guest.getOrganization().get();
                this.instance.organizationName = organization.getOrganizationName();
                this.instance.discountRate = organization.getDiscountRate();
            }
            this.instance.salutation = String.valueOf(guest.getSalutation());
            this.instance.firstName = guest.getFirstName();
            this.instance.lastName = guest.getLastName();
            this.instance.dateOfBirth = guest.getDateOfBirth();
            this.instance.addressStreet = guest.getAddress().getStreet();
            this.instance.addressZipcode = guest.getAddress().getZipcode();
            this.instance.addressCity = guest.getAddress().getCity();
            this.instance.addressCountry = String.valueOf(guest.getAddress().getCountry());
            this.instance.specialNotes = guest.getSpecialNotes();
            return this;
        }

        public GuestDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in GuestDTO");
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
        GuestDTO guestDTO = (GuestDTO) o;
        return Objects.equals(this.id, guestDTO.id) && Objects.equals(this.isOrganization, guestDTO.isOrganization) && Objects.equals(this.organizationName, guestDTO.organizationName) && Objects.equals(this.discountRate, guestDTO.discountRate) && Objects.equals(this.salutation, guestDTO.salutation) && Objects.equals(this.firstName, guestDTO.firstName) && Objects.equals(this.lastName, guestDTO.lastName) && Objects.equals(this.dateOfBirth, guestDTO.dateOfBirth) && Objects.equals(this.addressStreet, guestDTO.addressStreet) && Objects.equals(this.addressZipcode, guestDTO.addressZipcode) && Objects.equals(this.addressCity, guestDTO.addressCity) && Objects.equals(this.addressCountry, guestDTO.addressCountry) && Objects.equals(this.specialNotes, guestDTO.specialNotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.isOrganization, this.organizationName, this.discountRate, this.salutation, this.firstName, this.lastName, this.dateOfBirth, this.addressStreet, this.addressZipcode, this.addressCity, this.addressCountry, this.specialNotes);
    }
}
