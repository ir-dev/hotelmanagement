package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.Booking;
import at.fhv.hotelmanagement.domain.model.BookingNo;
import at.fhv.hotelmanagement.domain.model.Guest;
import at.fhv.hotelmanagement.domain.model.PaymentInformation;
import at.fhv.hotelmanagement.domain.model.enums.BookingStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Objects;

public final class BookingDTO {
    private BookingDetailsDTO details;

    private BookingNo bookingNo;
    private BookingStatus bookingStatus;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private LocalTime arrivalTime;
    private Integer numberOfPersons;

    public static Builder builder() {
        return new BookingDTO.Builder();
    }

    public BookingDetailsDTO details() {
        return this.details;
    }

    public BookingNo bookingNo() {
        return this.bookingNo;
    }

    public BookingStatus bookingStatus() {
        return this.bookingStatus;
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

    private BookingDTO() {
    }

    public static class Builder {
        private BookingDTO instance;

        private Builder() {
            this.instance = new BookingDTO();
        }

        public Builder withBookingEntity(Booking booking) {
            this.instance.bookingNo = booking.getBookingNo();
            this.instance.bookingStatus = booking.getBookingStatus();
            this.instance.arrivalDate = booking.getArrivalDate();
            this.instance.departureDate = booking.getDepartureDate();
            this.instance.arrivalTime = booking.getArrivalTime();
            this.instance.numberOfPersons = booking.getNumberOfPersons();
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
}
