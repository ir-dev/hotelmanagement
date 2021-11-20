package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.StayService;
import at.fhv.hotelmanagement.application.dto.StayDTO;
import at.fhv.hotelmanagement.domain.model.*;
import at.fhv.hotelmanagement.domain.model.enums.BookingState;
import at.fhv.hotelmanagement.domain.model.enums.RoomState;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import at.fhv.hotelmanagement.domain.repositories.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Component
public class StayServiceImpl implements StayService {

    @Autowired
    StayRepository stayRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Transactional(readOnly = true)
    @Override
    public List<StayDTO> allStays() {
        List<Stay> stays = stayRepository.findAll();
        List<StayDTO> staysDto = new ArrayList<>();

        for (Stay stay : stays) {
            staysDto.add(buildStayDto(stay));
        }

        return staysDto;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<StayDTO> stayByStayId(String stayId) {
        Optional<Stay> stay = stayRepository.findById(new StayId(stayId));
        if (stay.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(buildStayDto(stay.get()));
    }

    private StayDTO buildStayDto(Stay stay) {
        return StayDTO.builder()
                .withStayEntity(stay)
                .build();
    }

    @Transactional
    @Override
    public void createAndCheckinStayForBooking(String bookingNo) throws CreateStayException {
        Stay stay = new Stay(
                stayRepository.nextIdentity(),
                new BookingNo(bookingNo)
        );

        stayRepository.store(stay);

        Optional<Booking> booking = bookingRepository.findByNo(new BookingNo(bookingNo));

        if (booking.isEmpty()) {
            throw new IllegalArgumentException("Booking: " + bookingNo + " not found!");
        }

        LocalDate arrivalDate = booking.get().getArrivalDate();
        LocalDate departureDate = booking.get().getDepartureDate();

        assignRooms(
                stay.getStayId().getId(),
                booking.get().getSelectedCategoriesRoomCount(),
                arrivalDate,
                departureDate
        );

        booking.get().changeState(BookingState.CLOSED);
    }

    public void assignRooms(String stayId, Map<String, Integer> selectedCategories, LocalDate fromDate, LocalDate toDate) throws CreateStayException {

        for (Map.Entry<String, Integer> category : selectedCategories.entrySet()) {

            List<Room> availableRooms = categoryRepository.findCategoryRoomsByState(category.getKey(), RoomState.AVAILABLE);
            if (availableRooms.size() < category.getValue()) {
                throw new CreateStayException("Not enough rooms available of: " + category.getKey());
            }
            Iterator<Room> iterator = availableRooms.iterator();

            for (int i = 0; i < category.getValue(); i++) {

                Room room = iterator.next();

                // create RoomOccupancy
                categoryRepository.store(new RoomOccupancy(categoryRepository.nextIdentity(), room.getNumber(), fromDate, toDate));

                // change state of room to occupied
                room.changeState(RoomState.OCCUPIED);
            }
        }
    }

}
