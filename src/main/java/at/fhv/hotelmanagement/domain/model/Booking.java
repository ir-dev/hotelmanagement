package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.enums.BookingStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Booking {
    private String bookingNr;
    private String guestId;
    private LocalDate arrival;
    private LocalDate departure;
    private BookingStatus status;
    private int nrOfBookedRooms;
    private LocalTime arrivalTime;
    private List<Category> categories;
//    private CancellationModality cancellationModality;
    private Organization organization;
//    private List<AdditionalService> additionalServices;
    //private Address billingAddress;

    public Booking(String bookingNr, String guestId, LocalDate arrival, LocalDate departure, BookingStatus status,
                   int nrOfBookedRooms, LocalTime arrivalTime) {
        this.bookingNr = bookingNr;
        this.guestId = guestId;
        this.arrival = arrival;
        this.departure = departure;
        this.status = status;
        this.nrOfBookedRooms = nrOfBookedRooms;
        this.arrivalTime = arrivalTime;
    }

    public String bookingNr() {
        return this.bookingNr;
    }

    public String guestId() {
        return this.guestId;
    }

    public LocalDate arrival() {
        return this.arrival;
    }

    public LocalDate departure() {
        return this.departure;
    }

    public BookingStatus status() {
        return this.status;
    }

    public int nrOfBookedRooms() {
        return this.nrOfBookedRooms;
    }

    public LocalTime arrivalTime() {
        return this.arrivalTime;
    }

    public List<Category> categories() {
        return this.categories;
    }

//    public CancellationModality cancellationModality() {
//        return this.cancellationModality;
//    }

    public Organization organization() {
        return this.organization;
    }

//    public List<AdditionalService> additionalServices() {
//        return this.additionalServices;
//    }


}
