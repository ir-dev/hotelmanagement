package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.category.RoomNumber;
import at.fhv.hotelmanagement.domain.model.guest.PaymentInformation;
import at.fhv.hotelmanagement.domain.model.stay.Stay;
import java.time.LocalDateTime;
import java.util.*;


public class StayDetailsDTO {
    private Map<String, Integer> selectedCategoriesRoomCount;
    private String bookingNo;
    private GuestDTO guest;
    private String cardHolderName;
    private String cardNumber;
    private String cardValidThru;
    private String cardCvc;
    private String paymentType;
    private LocalDateTime checkedInAt;
    private LocalDateTime checkedOutAt;
    private Set<String> roomNumbers;


    public static StayDetailsDTO.Builder builder() {
        return new StayDetailsDTO.Builder();
    }

    public Map<String, Integer> selectedCategoriesRoomCount() {
        return this.selectedCategoriesRoomCount;
    }

    public String bookingNo() {
        return this.bookingNo;
    }

    public GuestDTO guest() {
        return this.guest;
    }

    public String cardHolderName() { return this.cardHolderName; }

    public String cardNumber() { return this.cardNumber; }

    public String cardValidThru() { return this.cardValidThru; }

    public String cardCvc() { return this.cardCvc; }

    public String paymentType() { return this.paymentType; }

    public LocalDateTime checkedInAt() {
        return this.checkedInAt;
    }

    public LocalDateTime checkedOutAt() {
        return this.checkedOutAt;
    }

    public Set<String> roomNumbers() {
        return this.roomNumbers;
    }

    private StayDetailsDTO() {
    }

    public static class Builder {
        private StayDetailsDTO instance;

        private Builder() {
            this.instance = new StayDetailsDTO();
        }

        public Builder withStayEntity(Stay stay) {
            this.instance.selectedCategoriesRoomCount = stay.getSelectedCategoriesRoomCount();
            if (stay.getBookingNo().isPresent()) {
                this.instance.bookingNo = stay.getBookingNo().get().getNo();
            }
            PaymentInformation guest = stay.getPaymentInformation();
            this.instance.cardHolderName = guest.getCardHolderName();
            this.instance.cardNumber = guest.getCardNumber();
            this.instance.cardValidThru = guest.getCardValidThru();
            this.instance.cardCvc = guest.getCardCvc();
            this.instance.paymentType = String.valueOf(guest.getPaymentType());
            this.instance.checkedInAt = stay.getCheckedInAt();
            if (stay.getCheckedOutAt().isPresent()) {
                this.instance.checkedOutAt = stay.getCheckedOutAt().get();
            }
            return this;
        }

        public Builder withGuestDTO(GuestDTO guest) {
            this.instance.guest = guest;
            return this;
        }

        public Builder withRoomNumbers(List<RoomNumber> rooms) {
            this.instance.roomNumbers =  new HashSet<>();
            for(RoomNumber rn : rooms) {
                this.instance.roomNumbers.add(rn.getNumber());
            }
            return this;
        }

        public StayDetailsDTO build() {
            Objects.requireNonNull(this.instance.selectedCategoriesRoomCount, "selectedCategoriesRoomCount must be set in StayDetailsDTO");
            Objects.requireNonNull(this.instance.guest, "guest must be set in StayDetailsDTO");
            Objects.requireNonNull(this.instance.cardHolderName, "cardHolderName must be set in StayDetailsDTO");
            Objects.requireNonNull(this.instance.cardNumber, "cardNumber must be set in StayDetailsDTO");
            Objects.requireNonNull(this.instance.cardValidThru, "cardValidThru must be set in StayDetailsDTO");
            Objects.requireNonNull(this.instance.cardCvc, "cardCvc must be set in StayDetailsDTO");
            Objects.requireNonNull(this.instance.paymentType, "paymentType must be set in StayDetailsDTO");
            Objects.requireNonNull(this.instance.checkedInAt, "checkedInAt must be set in StayDetailsDTO");
            Objects.requireNonNull(this.instance.roomNumbers, "roomNumbers must be set in StayDetailsDTO");
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
        StayDetailsDTO that = (StayDetailsDTO) o;
        return Objects.equals(this.selectedCategoriesRoomCount, that.selectedCategoriesRoomCount) && Objects.equals(this.bookingNo, that.bookingNo) && Objects.equals(this.guest, that.guest) && Objects.equals(this.cardHolderName, that.cardHolderName) && Objects.equals(this.cardNumber, that.cardNumber) && Objects.equals(this.cardValidThru, that.cardValidThru) && Objects.equals(this.cardCvc, that.cardCvc) && Objects.equals(this.paymentType, that.paymentType) && Objects.equals(this.checkedInAt, that.checkedInAt) && Objects.equals(this.checkedOutAt, that.checkedOutAt) && Objects.equals(this.roomNumbers, that.roomNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.selectedCategoriesRoomCount, this.bookingNo, this.guest, this.cardHolderName, this.cardNumber, this.cardValidThru, this.cardCvc, this.paymentType, this.checkedInAt, this.checkedOutAt, this.roomNumbers);
    }
}
