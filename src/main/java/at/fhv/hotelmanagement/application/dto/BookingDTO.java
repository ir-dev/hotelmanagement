package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.Booking;
import at.fhv.hotelmanagement.domain.model.Enums.BookingStatus;

import java.time.LocalDate;
import java.util.Objects;

public class BookingDTO {
    private String bookingNr;
    private String guestId;
    private LocalDate arrival;
    private LocalDate departure;
    private BookingStatus status;
    private int nrOfRooms;

    public static Builder builder() {
        return new Builder();
    }

    public LocalDate arrival() {
        return this.arrival;
    }

    public LocalDate departure() {
        return this.departure;
    }

    public String bookingNr() {
        return this.bookingNr;
    }

    public String guestId() {
        return this.guestId;
    }

    public String bookingStatus() {
        return this.status.toString();
    }

    public int nrOfRooms() {
        return this.nrOfRooms;
    }

    private BookingDTO() {

    }

    public static class Builder {
        private BookingDTO instance;

        private Builder() {
            this.instance = new BookingDTO();
        }

        public Builder withBookingEntity(Booking booking) {
            this.instance.bookingNr = booking.bookingNr();
            this.instance.guestId = booking.guestId();
            this.instance.arrival = booking.arrival();
            this.instance.departure = booking.departure();
            this.instance.status = booking.status();
            this.instance.nrOfRooms = booking.nrOfRooms();
            return this;
        }

        public BookingDTO build() {
            Objects.requireNonNull(this.instance.bookingNr, "id must be set in BookingDTO");
            Objects.requireNonNull(this.instance.arrival, "arrival must be set in BookingDTO");
            Objects.requireNonNull(this.instance.departure, "departure must be set in BookingDTO");

            return this.instance;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BookingDTO other = (BookingDTO) obj;
        if (bookingNr == null) {
            if (other.bookingNr != null) {
                return false;
            }
        } else if (!bookingNr.equals(other.bookingNr)) {
            return false;
        }

        return true;
    }


}
