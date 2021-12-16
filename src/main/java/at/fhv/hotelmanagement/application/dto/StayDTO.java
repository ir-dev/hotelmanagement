package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.stay.Stay;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public class StayDTO {
    private StayDetailsDTO details;
    private String stayId;
    private String stayStatus;
    private LocalDate departureDate;
    private Boolean isCheckedOut;

    public static Builder builder() { return new StayDTO.Builder(); }

    public GuestDTO guest() {
        return this.details.guest();
    }

    public Set<String> roomNumbers() {
        return this.details.roomNumbers();
    }

    public String stayId() { return this.stayId;}

    public String stayStatus() { return this.stayStatus;}


    public LocalDate departureDate() {
        return this.departureDate;
    }

    public Boolean isCheckedOut() {
        return this.isCheckedOut;
    }

    public LocalDate checkOutDate() {
        return this.details.checkedOutAt().toLocalDate();
    }

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
            this.instance.stayStatus = String.valueOf(stay.getStayState());
            this.instance.departureDate = stay.getDepartureDate();
            this.instance.isCheckedOut = stay.getCheckedOutAt().isPresent();
            return this;
        }

        public Builder withDetails(StayDetailsDTO details) {
            this.instance.details = details;
            return this;
        }

        public StayDTO build() {
            Objects.requireNonNull(this.instance.stayId, "stayId must be set in StayDTO");
            Objects.requireNonNull(this.instance.stayStatus, "stayStatus must be set in StayDTO");
            Objects.requireNonNull(this.instance.details, "details must be set in StayDTO");
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
        return Objects.equals(this.details, stayDTO.details) && Objects.equals(this.stayId, stayDTO.stayId) && Objects.equals(this.stayStatus, stayDTO.stayStatus) && Objects.equals(this.departureDate, stayDTO.departureDate) && Objects.equals(this.isCheckedOut, stayDTO.isCheckedOut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.details, this.stayId, this.stayStatus, this.departureDate, this.isCheckedOut);
    }
}