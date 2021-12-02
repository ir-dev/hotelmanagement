package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.stay.Stay;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public String getStayStateTextColorClass() {
        Objects.requireNonNull(this.stayStatus);

        switch (this.stayStatus) {
            case "CHECKED_IN":
                return "text-success";
            case "CHECKED_OUT":
                return "text-muted";
            default:
                return "";
        }
    }

    private StayDTO() {
    }

    public static class Builder {
        private StayDTO instance;

        private Builder() {this.instance = new StayDTO(); }

        public Builder withStayEntity(Stay stay) {
            this.instance.stayId = stay.getStayId().getId();
            this.instance.stayStatus = String.valueOf(stay.getStayStatus());
            if (stay.getBookingNo().isPresent()) {
                this.instance.bookingNo = stay.getBookingNo().get().getNo();
            }
            this.instance.checkedInAt = stay.getCheckedInAt();
            if (stay.getCheckedOutAt().isPresent()) {
                this.instance.checkedOutAt = stay.getCheckedOutAt().get();
            }
            return this;
        }

        public StayDTO build() {
            Objects.requireNonNull(this.instance.stayId, "stayId must be set in StayDTO");
            Objects.requireNonNull(this.instance.stayStatus, "stayStatus must be set in StayDTO");
            Objects.requireNonNull(this.instance.checkedInAt, "checkedInAt must be set in StayDTO");

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
        StayDTO stayDTO = (StayDTO) o;
        return Objects.equals(this.stayId, stayDTO.stayId) && Objects.equals(this.stayStatus, stayDTO.stayStatus) && Objects.equals(this.bookingNo, stayDTO.bookingNo) && Objects.equals(this.checkedInAt, stayDTO.checkedInAt) && Objects.equals(this.checkedOutAt, stayDTO.checkedOutAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.stayId, this.stayStatus, this.bookingNo, this.checkedInAt, this.checkedOutAt);
    }
}