package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.*;

import java.util.List;
import java.util.Objects;

public class BookingDetailsDTO {
    private BookingDTO details;
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

        public BookingDetailsDTO.Builder withBookingEntity(Booking booking) {
            //this.instance.categories = booking.categories();
            this.instance.cancellationModality = booking.cancellationModality();
            //this.instance.organization = booking.organization();
            this.instance.additionalServices = booking.additionalServices();
            //this.instance.billingAddress = booking.billingAddress();
            return this;
        }


        public BookingDetailsDTO build() {
            Objects.requireNonNull(this.instance.details, "details must be set in BookingDetailsDTO");
            //Objects.requireNonNull(this.instance.categories, "categories must be set in BookingDetailsDTO");
            //Objects.requireNonNull(this.instance.cancellationModality, "cancellationmodality must be set in BookingDetailsDTO");
//            if (this.instance.details.guestId() == null) {
//                Objects.requireNonNull(this.instance.organization, "organization must be set in BookingDetailsDTO");
//            } else {
//                Objects.requireNonNull(this.instance.details.guestId(), "guest must be set in BookingDetailsDTO");
//            }
            //Objects.requireNonNull(this.instance.additionalServices, "additionalservices must be set in BookingDetailsDTO");
            //Objects.requireNonNull(this.instance.billingAddress, "billingaddress must be set in BookingDetailsDTO");
            return this.instance;
        }
    }
}
