package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.BookingNo;
import at.fhv.hotelmanagement.domain.model.Stay;
import at.fhv.hotelmanagement.domain.model.StayId;
import at.fhv.hotelmanagement.domain.model.enums.StayStatus;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class StayDTO {
    private StayId stayId;
    private StayStatus stayStatus;
    private BookingNo bookingNo;
    private LocalDateTime checkedInAt;
    private Optional<LocalDateTime> checkedOutAt;

    public static Builder builder() { return new StayDTO.Builder(); }

    public StayId stayId() { return this.stayId;}

    public StayStatus stayStatus() { return this.stayStatus;}

    public BookingNo bookingNo() {
        return this.bookingNo;
    }

    public LocalDateTime checkedInAt() { return this.checkedInAt;}

    public Optional<LocalDateTime> checkedOutAt() { return this.checkedOutAt;}

    private StayDTO() {
    }

    public static class Builder {
        private StayDTO instance;

        private Builder() {this.instance = new StayDTO(); }

        public Builder withStayEntity(Stay stay) {
            this.instance.stayId = stay.getStayId();
            this.instance.stayStatus = stay.getStayStatus();
            this.instance.bookingNo = stay.getBookingNo();
            this.instance.checkedInAt = stay.getCheckedInAt();
            this.instance.checkedOutAt = stay.getCheckedOutAt();
            return this;
        }

        public StayDTO build() {
            Objects.requireNonNull(this.instance.stayId, "stayId must be set in StayDTO");
            Objects.requireNonNull(this.instance.stayStatus, "stayStatus must be set in StayDTO");
            Objects.requireNonNull(this.instance.bookingNo, "bookingNo must be set in StayDTO");
            Objects.requireNonNull(this.instance.checkedInAt, "checkedInAt must be set in StayDTO");
            Objects.requireNonNull(this.instance.checkedOutAt, "checkedOutAt must be set in StayDTO");

            return this.instance;
        }
    }


}