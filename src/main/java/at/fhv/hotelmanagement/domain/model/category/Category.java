package at.fhv.hotelmanagement.domain.model.category;

import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.stay.StayId;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Category {
    // generated hibernate id
    private Long id;
    private CategoryId categoryId;
    private String name;
    private String description;
    private Integer maxPersons;
    private Set<Room> rooms;
    private Price halfBoardPrice;
    private Price fullBoardPrice;

    // required for hibernate
    private Category() {}

    Category(CategoryId categoryId, String name, String description, Integer maxPersons, Price halfBoardPrice, Price fullBoardPrice) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.maxPersons = maxPersons;
        this.rooms = new HashSet<>();
        this.halfBoardPrice = halfBoardPrice;
        this.fullBoardPrice = fullBoardPrice;
    }

    public void createRoom(Room room) throws RoomAlreadyExistsException {
        if (!this.rooms.add(room)) {
            throw new RoomAlreadyExistsException(Room.class);
        }
    }

    public void assignAvailableRooms(Integer roomCount, LocalDate fromDate, LocalDate toDate, StayId stayId) throws InsufficientRoomsException {
        Set<Room> availableRooms = getAvailableRooms(fromDate, toDate);
        if (availableRooms.size() < roomCount) {
            throw new InsufficientRoomsException();
        }

        Iterator<Room> iterator = availableRooms.iterator();
        for (int i = 0; i < roomCount; i++) {
            Room room = iterator.next();

            // change state of room to occupied for given timespan
            room.occupied(fromDate, toDate, stayId);
        }
    }

    public void releaseRooms(List<RoomNumber> roomNumbers, StayId stayId) throws IllegalArgumentException {
        for (RoomNumber roomNumber : roomNumbers) {
            this.rooms.stream()
                    .filter(r -> r.getRoomNumber().equals(roomNumber))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Room number to release not found for given category."))
                    .cleaning(stayId);
        }
    }


    public Set<RoomNumber> getAvailableRoomNumbers(LocalDate fromDate, LocalDate toDate) {
        return getAvailableRooms(fromDate, toDate).stream()
                .map(Room::getRoomNumber)
                .collect(Collectors.toSet());
    }

    public int getAvailableRoomsCount(LocalDate fromDate, LocalDate toDate) {
        return getAvailableRooms(fromDate, toDate).size();
    }

    private Set<Room> getAvailableRooms(LocalDate fromDate, LocalDate toDate) {
        final Set<Room> availableRooms = new HashSet<>();

        for (Room room : this.rooms) {
            if (room.isAvailableForPeriod(fromDate, toDate)) {
                availableRooms.add(room);
            }
        }

        return Collections.unmodifiableSet(availableRooms);
    }


    public CategoryId getCategoryId() {
        return this.categoryId;

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

    public Price getHalfBoardPrice() {
        return this.halfBoardPrice;
    }

    public Price getFullBoardPrice() {
        return this.fullBoardPrice;
    }
}

