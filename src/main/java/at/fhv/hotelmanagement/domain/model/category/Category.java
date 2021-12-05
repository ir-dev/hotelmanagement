package at.fhv.hotelmanagement.domain.model.category;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class Category {
    // generated hibernate id
    private Long id;
    private CategoryId categoryId;
    private String name;
    private String description;
    private Integer maxPersons;
    private Set<Room> rooms;
    private Price price;

    // required for hibernate
    private Category() {}

    Category(CategoryId categoryId, String name, String description, Integer maxPersons) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.maxPersons = maxPersons;
        this.rooms = new HashSet<>();
    }

    public void createRoom(Room room) throws AlreadyExistsException {
        if (!this.rooms.add(room)) {
            throw new AlreadyExistsException(Room.class);
        }
    }

    public void assignAvailableRooms(Integer roomCount, LocalDate fromDate, LocalDate toDate) throws InsufficientRoomsException {
        Set<Room> availableRooms = getAvailableRooms(fromDate, toDate);
        if (availableRooms.size() < roomCount) {
            throw new InsufficientRoomsException();
        }

        Iterator<Room> iterator = availableRooms.iterator();
        for (int i = 0; i < roomCount; i++) {
            Room room = iterator.next();

            // change state of room to occupied for given timespan
            room.occupied(fromDate, toDate);
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
            if (room.isAvailable(fromDate, toDate)) {
                availableRooms.add(room);
            }
        }

        return Collections.unmodifiableSet(availableRooms);
    }
  
    public CategoryId getCategoryId() {
        return this.categoryId;

    }

    public void determinePrice(Price price) {
        if(price != null) {
            this.price = price;
        } else {
            throw new NullPointerException();
        }
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

    public Price getPrice() {
        return this.price;
    }

}

