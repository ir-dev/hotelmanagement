package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.booking.Booking;
import at.fhv.hotelmanagement.domain.model.guest.PaymentInformation;

import java.util.Map;
import java.util.Objects;

public final class BookingDetailsDTO {
    private Map<String, Integer> selectedCategoriesRoomCount;
    private GuestDTO guest;
    private String cardHolderName;
    private String cardNumber;
    private String cardValidThru;
    private String cardCvc;
    private String paymentType;

    public static BookingDetailsDTO.Builder builder() {
        return new BookingDetailsDTO.Builder();
    }

    public Map<String, Integer> selectedCategoriesRoomCount() {
        return this.selectedCategoriesRoomCount;
    }

    public GuestDTO guest() {
        return this.guest;
    }

    public String cardHolderName() { return this.cardHolderName; }

    public String cardNumber() { return this.cardNumber; }

    public String cardValidThru() { return this.cardValidThru; }

    public String cardCvc() { return this.cardCvc; }

    public String paymentType() { return this.paymentType; }

    private BookingDetailsDTO() {
    }

    public static class Builder {
        private BookingDetailsDTO instance;

        private Builder() {
            this.instance = new BookingDetailsDTO();
        }

        public BookingDetailsDTO.Builder withBookingEntity(Booking booking) {
            this.instance.selectedCategoriesRoomCount = booking.getSelectedCategoriesRoomCount();
            PaymentInformation guest = booking.getPaymentInformation();
            this.instance.cardHolderName = guest.getCardHolderName();
            this.instance.cardNumber = guest.getCardNumber();
            this.instance.cardValidThru = guest.getCardValidThru();
            this.instance.cardCvc = guest.getCardCvc();
            this.instance.paymentType = String.valueOf(guest.getPaymentType());
            return this;
        }

        public Builder withGuestDTO(GuestDTO guest) {
            this.instance.guest = guest;
            return this;
        }

        public BookingDetailsDTO build() {
            Objects.requireNonNull(this.instance.selectedCategoriesRoomCount, "selectedCategoriesRoomCount must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.guest, "guest must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.cardHolderName, "cardHolderName must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.cardNumber, "cardNumber must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.cardValidThru, "cardValidThru must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.cardCvc, "cardCvc must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.paymentType, "paymentType must be set in BookingDetailsDTO");


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
        BookingDetailsDTO that = (BookingDetailsDTO) o;
        return Objects.equals(this.selectedCategoriesRoomCount, that.selectedCategoriesRoomCount) && Objects.equals(this.guest, that.guest) && Objects.equals(this.cardHolderName, that.cardHolderName) && Objects.equals(this.cardNumber, that.cardNumber) && Objects.equals(this.cardValidThru, that.cardValidThru) && Objects.equals(this.cardCvc, that.cardCvc) && Objects.equals(this.paymentType, that.paymentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.selectedCategoriesRoomCount, this.guest, this.cardHolderName, this.cardNumber, this.cardValidThru, this.cardCvc, this.paymentType);
    }
}
