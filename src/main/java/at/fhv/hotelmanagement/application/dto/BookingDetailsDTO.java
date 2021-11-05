package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class BookingDetailsDTO {
    private BookingDTO details;
    private LocalTime arrivalTime;
    private List<CategoryDTO> categories;
    private CancellationModality cancellationModality;
    private OrganizationDTO organization;
    private List<AdditionalService> additionalServices;
    private Address billingAddress;

    public static BookingDetailsDTO.Builder builder() {
        return new BookingDetailsDTO.Builder();
    }

    public BookingDTO details() {
        return this.details;
    }

    public LocalTime arrivalTime() {
        return this.arrivalTime;
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

        public BookingDetailsDTO.Builder withBookingDTO(BookingDTO details) {
            this.instance.details = details;
            return this;
        }

        public BookingDetailsDTO.Builder withArrivalTime(LocalTime arrivalTime) {
            this.instance.arrivalTime = arrivalTime;
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

        public BookingDetailsDTO.Builder withAdditionalServices(List<AdditionalService> additionalServices) {
            this.instance.additionalServices = additionalServices;
            return this;
        }

        public BookingDetailsDTO.Builder withBillingAddress(Address billingAddress) {
            this.instance.billingAddress = billingAddress;
            return this;
        }

        public BookingDetailsDTO.Builder withBookingEntity(Booking booking) {
            this.instance.arrivalTime = booking.arrivalTime();
            //this.instance.categories = booking.categories();
            this.instance.cancellationModality = booking.cancellationModality();
            //this.instance.organization = booking.organization();
            this.instance.additionalServices = booking.additionalServices();
            this.instance.billingAddress = booking.billingAddress();
            return this;
        }


        public BookingDetailsDTO build() {
            Objects.requireNonNull(this.instance.details, "details must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.arrivalTime, "arrivaltime must be set in BookingDetailsDTO");
            //Objects.requireNonNull(this.instance.categories, "categories must be set in BookingDetailsDTO");
            //Objects.requireNonNull(this.instance.cancellationModality, "cancellationmodality must be set in BookingDetailsDTO");
//            if (this.instance.details.guestId() == null) {
//                Objects.requireNonNull(this.instance.organization, "organization must be set in BookingDetailsDTO");
//            } else {
//                Objects.requireNonNull(this.instance.details.guestId(), "guest must be set in BookingDetailsDTO");
//            }
            //Objects.requireNonNull(this.instance.additionalServices, "additionalservices must be set in BookingDTO");
            Objects.requireNonNull(this.instance.billingAddress, "billingaddress must be set in BookingDTO");
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
        return Objects.equals(this.details, that.details) && Objects.equals(this.arrivalTime, that.arrivalTime) && Objects.equals(this.categories, that.categories) && Objects.equals(this.cancellationModality, that.cancellationModality) && Objects.equals(this.organization, that.organization) && Objects.equals(this.additionalServices, that.additionalServices) && Objects.equals(this.billingAddress, that.billingAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.details, this.arrivalTime, this.categories, this.cancellationModality, this.organization, this.additionalServices, this.billingAddress);
    }
}
