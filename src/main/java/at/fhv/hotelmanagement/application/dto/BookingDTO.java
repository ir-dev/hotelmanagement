package at.fhv.hotelmanagement.application.dto;

import java.time.LocalDate;
import java.util.Objects;

public class BookingDTO {
    private String bookingNr;
    private String guestName;
    private LocalDate arrival;
    private LocalDate departure;
    private BookingStatus status;
    private int nrOfRooms;
    //    private BookingDetailsDTO details;

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

    public String guestName() {
        return this.guestName;
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

        public Builder withBookingNr(String bookingNr) {
            this.instance.bookingNr = bookingNr;
            return this;
        }

        public Builder withGuestName(String name) {
            this.instance.guestName = name;
            return this;
        }

        public Builder withArrival(LocalDate arrival) {
            this.instance.arrival = arrival;
            return this;
        }

        public Builder withDeparture(LocalDate departure) {
            this.instance.departure = departure;
            return this;
        }

        public Builder withBookingStatus(BookingStatus status) {
            this.instance.status = status;
            return this;
        }

        public Builder withNrOfRooms(int nrOfRooms) {
            this.instance.nrOfRooms = nrOfRooms;
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
