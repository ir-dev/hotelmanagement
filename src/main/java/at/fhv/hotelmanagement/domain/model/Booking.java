package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.application.dto.CategoryDTO;
import at.fhv.hotelmanagement.application.dto.OrganizationDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Booking {
    private String bookingNr;
    private String guestId;
    private LocalDate arrival;
    private LocalDate departure;
    private BookingStatus status;
    private int nrOfRooms;
    private LocalTime arrivalTime;
    private List<Category> categories;
    private CancellationModality cancellationModality;
    private Organization organization;
    private List<AdditionalService> additionalServices;
    private Address billingAddress;

    public Booking(String bookingNr, String guestId, LocalDate arrival, LocalDate departure, BookingStatus status,
                   int nrOfRooms, LocalTime arrivalTime, Address billingAddress) {
        this.bookingNr = bookingNr;
        this.guestId = guestId;
        this.arrival = arrival;
        this.departure = departure;
        this.status = status;
        this.nrOfRooms = nrOfRooms;
        this.arrivalTime = arrivalTime;
        this.billingAddress = billingAddress;
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

    public int nrOfRooms() {
        return this.nrOfRooms;
    }

    public LocalTime arrivalTime() {
        return this.arrivalTime;
    }

    public List<Category> categories() {
        return this.categories;
    }

    public CancellationModality cancellationModality() {
        return this.cancellationModality;
    }

    public Organization organization() {
        return this.organization;
    }

    public List<AdditionalService> additionalServices() {
        return this.additionalServices;
    }

    public Address billingAddress() {
        return this.billingAddress;
    }
}
