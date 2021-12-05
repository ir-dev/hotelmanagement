package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.Booking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public final class BookingDTO {
    private BookingDetailsDTO details;

    private String bookingNo;
    private String bookingState;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private LocalTime arrivalTime;
    private Integer numberOfPersons;
    private Integer numberOfBookedRooms;

    public static Builder builder() {
        return new BookingDTO.Builder();
    }

    public BookingDetailsDTO details() {
        return this.details;
    }

    public String bookingNo() {
        return this.bookingNo;
    }

    public String bookingState() {
        return this.bookingState;
    }

    public LocalDate arrivalDate() {
        return this.arrivalDate;
    }

    public LocalDate departureDate() {
        return this.departureDate;
    }

    public LocalTime arrivalTime() {
        return this.arrivalTime;
    }

    public Integer numberOfPersons() {
        return this.numberOfPersons;
    }

    public Integer numberOfBookedRooms() {
        return this.numberOfBookedRooms;
    }

    public String getBookingStateTextColorClass() {
        Objects.requireNonNull(this.bookingState);

        switch (this.bookingState) {
            case "PENDING":
                return "text-info";
            case "CONFIRMED":
                return "text-success";
            case "EXPIRED":
                return "text-warning";
            case "CLOSED":
                return "text-muted";
            case "CANCELLED":
                return "text-danger";
            default:
                return "";
        }
    }

    private BookingDTO() {
    }

    public static class Builder {
        private BookingDTO instance;

        private Builder() {
            this.instance = new BookingDTO();
        }

        public Builder withBookingEntity(Booking booking) {
            this.instance.bookingNo = booking.getBookingNo().getNo();
            this.instance.bookingState = String.valueOf(booking.getBookingState());
            this.instance.arrivalDate = booking.getArrivalDate();
            this.instance.departureDate = booking.getDepartureDate();
            this.instance.arrivalTime = booking.getArrivalTime();
            this.instance.numberOfPersons = booking.getNumberOfPersons();
            this.instance.numberOfBookedRooms = booking.getNumberOfBookedRooms();
            return this;
        }

        public Builder withDetails(BookingDetailsDTO details) {
            this.instance.details = details;
            return this;
        }

        public BookingDTO build() {
            Objects.requireNonNull(this.instance.bookingNo, "bookingNo must be set in BookingDTO");
            Objects.requireNonNull(this.instance.details, "details must be set in BookingDTO");

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
        BookingDTO that = (BookingDTO) o;
        return Objects.equals(this.details, that.details) && Objects.equals(this.bookingNo, that.bookingNo) && Objects.equals(this.bookingState, that.bookingState) && Objects.equals(this.arrivalDate, that.arrivalDate) && Objects.equals(this.departureDate, that.departureDate) && Objects.equals(this.arrivalTime, that.arrivalTime) && Objects.equals(this.numberOfPersons, that.numberOfPersons) && Objects.equals(this.numberOfBookedRooms, that.numberOfBookedRooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.details, this.bookingNo, this.bookingState, this.arrivalDate, this.departureDate, this.arrivalTime, this.numberOfPersons, this.numberOfBookedRooms);
    }
}
