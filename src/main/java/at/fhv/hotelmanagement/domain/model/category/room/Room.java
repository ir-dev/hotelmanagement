package at.fhv.hotelmanagement.domain.model.category.room;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Room {
    // generated hibernate id
    private Long id;
    private RoomNumber roomNumber;
    private RoomState roomState;
    private Set<RoomOccupancy> roomOccupancies;

    // required for hibernate
    private Room() {
    }

    public Room(RoomNumber roomNumber, RoomState roomState) {
        // disallow (resp. explicitly allow) some initial room states (this must be always conform with all room state changes!!)
        if (!(roomState == RoomState.AVAILABLE || roomState == RoomState.MAINTENANCE || roomState == RoomState.CLEANING)) {
            throw new IllegalArgumentException("Forbidden initial room state: " + roomState);
        }

        this.roomNumber = roomNumber;
        this.roomState = roomState;
        this.roomOccupancies = new HashSet<>();
    }

    public boolean isAvailable(LocalDate fromDate, LocalDate toDate) {
        // check for intersection from at least one roomOccupancy with given period
        for (RoomOccupancy roomOccupancy : this.roomOccupancies) {
            // find overlaps because (StartA <= EndB) and (EndA >= StartB) (see also https://stackoverflow.com/a/325964/12511726)
            if (fromDate.compareTo(roomOccupancy.getEndDate()) <= 0 && toDate.compareTo(roomOccupancy.getStartDate()) >= 0) {
                return false;
            }
        }

        return true;
    }

    public void occupied(LocalDate fromDate, LocalDate toDate) throws IllegalStateException {
        createRoomOccupancy(fromDate, toDate);
        this.roomState = RoomState.OCCUPIED;
    }

    private void createRoomOccupancy(LocalDate fromDate, LocalDate toDate) throws IllegalStateException {
        if (!isAvailable(fromDate, toDate)) {
            throw new IllegalStateException("Only room with AVAILABLE status can be occupied.");
        }

        RoomOccupancy roomOccupancy = new RoomOccupancy(fromDate, toDate);
        this.roomOccupancies.add(roomOccupancy);
    }

    public RoomNumber getRoomNumber() {
        return this.roomNumber;
    }

    public Set<RoomOccupancy> getRoomOccupancies() {
        return Collections.unmodifiableSet(this.roomOccupancies);
    }

    //Needed for AlreadyExistsException
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return Objects.equals(this.id, room.id) && Objects.equals(this.roomNumber, room.roomNumber) && this.roomState == room.roomState && Objects.equals(this.roomOccupancies, room.roomOccupancies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.roomNumber, this.roomState, this.roomOccupancies);
    }
}