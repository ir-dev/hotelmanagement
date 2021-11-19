package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.BookingNo;
import at.fhv.hotelmanagement.domain.model.Stay;
import at.fhv.hotelmanagement.domain.model.StayId;
import at.fhv.hotelmanagement.domain.model.enums.StayStatus;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class StayDTO {
    private String stayId;
    private String stayStatus;
    private String bookingNo;
    private LocalDateTime checkedInAt;
    private LocalDateTime checkedOutAt;

    public static Builder builder() { return new StayDTO.Builder(); }

    public String stayId() { return this.stayId;}

    public String stayStatus() { return this.stayStatus;}

    public String bookingNo() {
        return this.bookingNo;
    }

    public LocalDateTime checkedInAt() { return this.checkedInAt;}

    public LocalDateTime checkedOutAt() { return this.checkedOutAt;}

    private StayDTO() {
    }

    public static class Builder {
        private StayDTO instance;

        private Builder() {this.instance = new StayDTO(); }

        public Builder withStayEntity(Stay stay) {
            this.instance.stayId = stay.getStayId().getId();
            this.instance.stayStatus = String.valueOf(stay.getStayStatus());
            this.instance.bookingNo = stay.getBookingNo().getNo();
            this.instance.checkedInAt = stay.getCheckedInAt();
            if (stay.getCheckedOutAt().isPresent()) {
                this.instance.checkedOutAt = stay.getCheckedOutAt().get();
            }
            return this;
        }

        public StayDTO build() {
            Objects.requireNonNull(this.instance.stayId, "stayId must be set in StayDTO");
            Objects.requireNonNull(this.instance.stayStatus, "stayStatus must be set in StayDTO");
            Objects.requireNonNull(this.instance.bookingNo, "bookingNo must be set in StayDTO");
            Objects.requireNonNull(this.instance.checkedInAt, "checkedInAt must be set in StayDTO");

            return this.instance;
        }
    }


}