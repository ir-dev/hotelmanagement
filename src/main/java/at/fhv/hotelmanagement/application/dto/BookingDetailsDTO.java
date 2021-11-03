package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class BookingDetailsDTO {
    private String bookingNr;
    private LocalDate arrival;
    private LocalDate departure;
    private BookingStatus status;
    private List<CategoryDTO> categories;
    private CancellationModality cancellationModality;
    private OrganizationDTO organization;
    private GuestDTO guest;
    private List<AdditionalService> additionalServices;
    private Address billingaddress;

    public static BookingDetailsDTO.Builder builder() {
        return new BookingDetailsDTO.Builder();
    }

    public String bookingNr() {
        return this.bookingNr;
    }

    public LocalDate arrival() {
        return this.arrival;
    }

    public LocalDate departure() {
        return this.departure;
    }

    public String bookingStatus() {
        return this.status.toString();
    }

    public List<CategoryDTO> categories() {
        return this.categories;
    }

    public String cancellationModality() {
        return this.cancellationModality.toString();
    }

    public OrganizationDTO organization() {
        return this.organization;
    }

    public GuestDTO guest() {
        return this.guest;
    }

    public List<AdditionalService> additionalService() {
        return this.additionalServices;
    }

    public Address address() {
        return this.billingaddress;
    }

    private BookingDetailsDTO() {

    }

    public static class Builder {
        private BookingDetailsDTO instance;

        private Builder() {
            this.instance = new BookingDetailsDTO();
        }

        public BookingDetailsDTO.Builder withBookingNr(String bookingNr) {
            this.instance.bookingNr = bookingNr;
            return this;
        }

        public BookingDetailsDTO.Builder withArrival(LocalDate arrival) {
            this.instance.arrival = arrival;
            return this;
        }

        public BookingDetailsDTO.Builder withDeparture(LocalDate departure) {
            this.instance.departure = departure;
            return this;
        }

        public BookingDetailsDTO.Builder withBookingStatus(BookingStatus status) {
            this.instance.status = status;
            return this;
        }

        public BookingDetailsDTO.Builder withCategories(List<CategoryDTO> categories) {
            this.instance.categories = categories;
            return this;
        }

        public BookingDetailsDTO.Builder withCancellationModality(CancellationModality cancellationModality) {
            this.instance.cancellationModality = cancellationModality;
            return this;
        }

        public BookingDetailsDTO.Builder withOrganization(OrganizationDTO organization) {
            this.instance.organization = organization;
            return this;
        }

        public BookingDetailsDTO.Builder withGuest(GuestDTO guest) {
            this.instance.guest = guest;
            return this;
        }

        public BookingDetailsDTO.Builder withBillingAddress(Address billingAddress) {
            this.instance.billingaddress = billingAddress;
            return this;
        }

        public BookingDetailsDTO.Builder withAdditionalServices(List<AdditionalService> additionalServices) {
            this.instance.additionalServices = additionalServices;
            return this;
        }

        public BookingDetailsDTO build() {
            Objects.requireNonNull(this.instance.bookingNr, "id must be set in BookingDTO");
            Objects.requireNonNull(this.instance.arrival, "arrival must be set in BookingDTO");
            Objects.requireNonNull(this.instance.departure, "departure must be set in BookingDTO");
            Objects.requireNonNull(this.instance.status, "status must be set in BookingDTO");
            Objects.requireNonNull(this.instance.categories, "categories must be set in BookingDTO");
            Objects.requireNonNull(this.instance.cancellationModality, "cancellationmodality must be set in BookingDTO");
            if (this.instance.guest == null) {
                Objects.requireNonNull(this.instance.organization, "organization must be set in BookingDTO");
            } else {
                Objects.requireNonNull(this.instance.guest, "guest must be set in BookingDTO");
            }
            //            Objects.requireNonNull(this.instance.additionalServices, "additionalservices must be set in BookingDTO");
            //            Objects.requireNonNull(this.instance.billingaddress, "billingaddress must be set in BookingDTO");

            return this.instance;
        }
    }
}
