package at.fhv.hotelmanagement.domain.model;

import java.time.LocalDate;
import java.util.List;

public class Booking {
    private final Long id;
//    private Guest guest;
    private int nrOfRoomsOfCategory;
    private LocalDate arrive;
    private LocalDate departure;
    private List<Category> categoryList;


    public Booking(Long id) {
        this.id = id;
    }
}
