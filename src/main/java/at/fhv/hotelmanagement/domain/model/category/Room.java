package at.fhv.hotelmanagement.domain.model.category;

import at.fhv.hotelmanagement.domain.model.stay.StayId;

import java.time.LocalDate;
import java.util.HashSet;
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
        if (!(roomState == RoomState.AVAILABLE || roomState == RoomState.CLEANING)) {
            throw new IllegalArgumentException("Forbidden initial room state: " + roomState);
        }

        this.roomNumber = roomNumber;
        this.roomState = roomState;
        this.roomOccupancies = new HashSet<>();
    }

    // isAvailable(21.12.2021, 24.12.2021) -> is available even when there is intersection with a roomOccupancy ending on that fromDate
    // Room: [RoomOccupancy from 18.12.2021 - 22.12.2021, ..]   -> not available
    // Room: [RoomOccupancy from 18.12.2021 - 21.12.2021]       -> available
    // Room: [RoomOccupancy from 24.12.2021 - 31.12.2021]       -> available
    public boolean isAvailableForPeriod(LocalDate fromDate, LocalDate toDate) {
        // check for intersection from at least one roomOccupancy with given period
        for (RoomOccupancy roomOccupancy : this.roomOccupancies) {
            // find overlaps because (StartA <= EndB) and (EndA >= StartB) (see also https://stackoverflow.com/a/325964/12511726)
            if (fromDate.compareTo(roomOccupancy.getEndDate()) < 0 && toDate.compareTo(roomOccupancy.getStartDate()) > 0) {
                return false;
            }
        }

        return true;
    }

    public void available() throws IllegalStateException {
        this.roomState = RoomState.AVAILABLE;
    }

    public void maintenance() throws IllegalStateException {
        this.roomState = RoomState.MAINTENANCE;
    }

    public void occupied(LocalDate fromDate, LocalDate toDate, StayId stayId) throws IllegalStateException {
        createRoomOccupancy(fromDate, toDate, stayId);
        this.roomState = RoomState.OCCUPIED;
    }

    public void cleaning(StayId stayId) {
        // muss occupied sein!

        updateRoomOccupanciesToDate(stayId);
        this.roomState = RoomState.CLEANING;
    }

    public void cleaning() {
        this.roomState = RoomState.CLEANING;
    }

    private void createRoomOccupancy(LocalDate fromDate, LocalDate toDate, StayId stayId) throws IllegalStateException {
        if (!isAvailableForPeriod(fromDate, toDate)) {
            throw new IllegalStateException("Room is not available for given period.");
        }

        RoomOccupancy roomOccupancy = new RoomOccupancy(fromDate, toDate, stayId);
        this.roomOccupancies.add(roomOccupancy);
    }

    private void updateRoomOccupanciesToDate(StayId stayId) {
        for (RoomOccupancy roomOccupancy : this.roomOccupancies) {
            if (roomOccupancy.getStayId().equals(stayId)) {
                roomOccupancy.modifyEndDate(LocalDate.now());
            }
        }
    }

    public RoomNumber getRoomNumber() {
        return this.roomNumber;
    }

    public RoomState getRoomState() {
        return this.roomState;
    }
}