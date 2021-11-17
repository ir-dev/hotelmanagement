package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class BookingDetailsDTO {
    private Map<String, Integer> selectedCategoriesRoomCount;
    private GuestDTO guest;
    private PaymentInformation paymentInformation;

    public static BookingDetailsDTO.Builder builder() {
        return new BookingDetailsDTO.Builder();
    }

    public Map<String, Integer> selectedCategoriesRoomCount() {
        return this.selectedCategoriesRoomCount;
    }

    public GuestDTO guest() {
        return this.guest;
    }

    public PaymentInformation paymentInformation() {
        return this.paymentInformation;
    }

    private BookingDetailsDTO() {
    }

    public static class Builder {
        private BookingDetailsDTO instance;

        private Builder() {
            this.instance = new BookingDetailsDTO();
        }

        public BookingDetailsDTO.Builder withBookingEntity(Booking booking) {
            this.instance.selectedCategoriesRoomCount = booking.getSelectedCategoriesRoomCount();
            this.instance.paymentInformation = booking.getPaymentInformation();
            return this;
        }

        public Builder withGuestDTO(GuestDTO guest){
            this.instance.guest = guest;
            return this;
        }

        public BookingDetailsDTO build() {
            Objects.requireNonNull(this.instance.selectedCategoriesRoomCount, "selectedCategoriesRoomCount must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.guest, "guest must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.paymentInformation, "paymentInformation must be set in BookingDetailsDTO");

            return this.instance;
        }
    }
}
