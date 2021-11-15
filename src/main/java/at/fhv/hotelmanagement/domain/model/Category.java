package at.fhv.hotelmanagement.domain.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Category {
    private Long id;
    private String name;
    private String description;
    private Integer maxPersons;
    private Set<Room> rooms;

    private Category() {}

    public Category(String name, String description, Integer maxPersons, Set<Room> rooms) {
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

    public Set<Room> getRooms() {
        return Collections.unmodifiableSet(this.rooms);
    }

    public Set<Room> getAvailableRooms(LocalDate fromDate, LocalDate toDate) {
        final Set<Room> availableRooms = new HashSet<>();

        for (Room room : rooms) {
            if(room.isAvailable(fromDate, toDate)) {
                availableRooms.add(room);
            }
        }

        return Collections.unmodifiableSet(availableRooms);
    }

    public int getAvailableRoomsCount(LocalDate fromDate, LocalDate toDate) {
        return getAvailableRooms(fromDate, toDate).size();
    }
}

