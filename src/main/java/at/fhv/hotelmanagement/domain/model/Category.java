package at.fhv.hotelmanagement.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Category {
    private Long id;
    private String name;
    private String description;
    private Integer maxPersons;
    private List<Room> rooms;

    private Category() {}

    public Category(String name, String description, Integer maxPersons, List<Room> rooms) {
        this.name = name;
        this.description = description;
        this.maxPersons = maxPersons;
        this.rooms = rooms;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getMaxPersons() {
        return this.maxPersons;
    }

    public List<Room> getRooms() {
        return Collections.unmodifiableList(this.rooms);
    }

    public List<Room> getAvailableRooms(LocalDate fromDate, LocalDate toDate) {
        final List<Room> availableRooms = new ArrayList<>();

        for (Room room : rooms) {
            if(room.isAvailable(fromDate, toDate)) {
                availableRooms.add(room);
            }
        }

        return Collections.unmodifiableList(availableRooms);
    }

    public int getAvailableRoomsCount(LocalDate fromDate, LocalDate toDate) {
        return getAvailableRooms(fromDate, toDate).size();
    }
}

