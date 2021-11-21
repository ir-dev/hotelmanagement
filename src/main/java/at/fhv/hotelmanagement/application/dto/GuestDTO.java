package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.Guest;
import at.fhv.hotelmanagement.domain.model.Organization;

import java.time.LocalDate;
import java.util.Objects;

public final class GuestDTO {
    private String id;
    private Boolean isOrganization;
    private String organizationName;
    private String organizationAgreementCode;
    private String salutation;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
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

    public String organizationAgreementCode() { return this.organizationAgreementCode; }

    public String salutation() {
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
                this.instance.organizationAgreementCode = organization.getOrganizationAgreementCode();
            }
            this.instance.salutation = String.valueOf(guest.getSalutation());
            this.instance.firstName = guest.getFirstName();
            this.instance.lastName = guest.getLastName();
            this.instance.birthday = guest.getBirthday();
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
}
