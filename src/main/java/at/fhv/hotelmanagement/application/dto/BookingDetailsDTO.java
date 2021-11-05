package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class BookingDetailsDTO {
    private String bookingNr;
    private LocalDate arrival;
    private LocalTime arrivalTime;
    private LocalDate departure;
    private LocalDate optionDate;
    private BookingStatus status;
    private List<CategoryDTO> categories;
    private CancellationModality cancellationModality;
    private OrganizationDTO organization;
    private GuestDTO guest;
    private List<AdditionalService> additionalServices;
    private Address billingAddress;

    public static BookingDetailsDTO.Builder builder() {
        return new BookingDetailsDTO.Builder();
    }

    public String bookingNr() {
        return this.bookingNr;
    }

    public LocalDate arrival() {
        return this.arrival;
    }

    public LocalTime arrivalTime() {
        return this.arrivalTime;
    }

    public LocalDate departure() {
        return this.departure;
    }

    public LocalDate optionDate() {
        return this.optionDate;
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
        return this.billingAddress;
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

        public BookingDetailsDTO.Builder withArrivalTime(LocalTime arrivalTime) {
            this.instance.arrivalTime = arrivalTime;
            return this;
        }

        public BookingDetailsDTO.Builder withDeparture(LocalDate departure) {
            this.instance.departure = departure;
            return this;
        }

        public BookingDetailsDTO.Builder withOptionDate(LocalDate optionDate) {
            this.instance.optionDate = optionDate;
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
            this.instance.billingAddress = billingAddress;
            return this;
        }

        public BookingDetailsDTO.Builder withAdditionalServices(List<AdditionalService> additionalServices) {
            this.instance.additionalServices = additionalServices;
            return this;
        }

        public BookingDetailsDTO build() {
            Objects.requireNonNull(this.instance.bookingNr, "id must be set in BookingDTO");
            Objects.requireNonNull(this.instance.arrival, "arrival must be set in BookingDTO");
            Objects.requireNonNull(this.instance.arrivalTime, "arrivaltime must be set in BookingDTO");
            Objects.requireNonNull(this.instance.departure, "departure must be set in BookingDTO");
            Objects.requireNonNull(this.instance.optionDate, "optiondate must be set in BookingDTO");
            Objects.requireNonNull(this.instance.status, "status must be set in BookingDTO");
            Objects.requireNonNull(this.instance.categories, "categories must be set in BookingDTO");
            Objects.requireNonNull(this.instance.cancellationModality, "cancellationmodality must be set in BookingDTO");
            if (this.instance.guest == null) {
                Objects.requireNonNull(this.instance.organization, "organization must be set in BookingDTO");
            } else {
                Objects.requireNonNull(this.instance.guest, "guest must be set in BookingDTO");
            }
            //            Objects.requireNonNull(this.instance.additionalServices, "additionalservices must be set in BookingDTO");
            //            Objects.requireNonNull(this.instance.billingAddress, "billingaddress must be set in BookingDTO");

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
        BookingDetailsDTO that = (BookingDetailsDTO) o;
        return Objects.equals(this.bookingNr, that.bookingNr) && Objects.equals(this.arrival, that.arrival) && Objects.equals(this.arrivalTime, that.arrivalTime) && Objects.equals(this.departure, that.departure) && Objects.equals(this.optionDate, that.optionDate) && this.status == that.status && Objects.equals(this.categories, that.categories) && Objects.equals(this.cancellationModality, that.cancellationModality) && Objects.equals(this.organization, that.organization) && Objects.equals(this.guest, that.guest) && Objects.equals(this.additionalServices, that.additionalServices) && Objects.equals(this.billingAddress, that.billingAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.bookingNr, this.arrival, this.arrivalTime, this.departure, this.optionDate, this.status, this.categories, this.cancellationModality, this.organization, this.guest, this.additionalServices, this.billingAddress);
    }
}
