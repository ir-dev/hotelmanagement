package at.fhv.hotelmanagement.domain.model;

import java.time.LocalDate;

public class Booking {
    private final Long id;
//    private Guest guest;
    private int nrOfRoomsOfCategory;
    private LocalDate arrive;
    private LocalDate departure;


    public Booking(Long id) {
        this.id = id;
    }
}
