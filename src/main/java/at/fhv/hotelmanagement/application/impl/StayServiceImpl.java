package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.CategoryService;
import at.fhv.hotelmanagement.application.api.StayService;
import at.fhv.hotelmanagement.application.dto.StayDTO;
import at.fhv.hotelmanagement.domain.model.*;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
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
    CategoryService categoryService;

    @Autowired
    BookingRepository bookingRepository;

    //    @Autowired
    //    RoomRepository roomRepository;

    //    @Autowired
    //    RoomOccupationRepository roomOccupationRepository;

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
    public void createAndCheckinStayForBooking(String bookingNo) {
        Stay stay = new Stay(
                stayRepository.nextIdentity(),
                new BookingNo(bookingNo)
        );

        stayRepository.store(stay);

        Optional<Booking> booking = bookingRepository.findByNo(new BookingNo(bookingNo));
        if (booking.isEmpty()) {
            throw new IllegalArgumentException("Bookingnumber: " + bookingNo + " not found!");
        }

        LocalDate arrivalDate = booking.get().getArrivalDate();
        LocalDate departureDate = booking.get().getDepartureDate();

        assignRooms(stay.getStayId().getId(),
                categoryService.getAvailableRooms(
                        booking.get().getSelectedCategoriesRoomCount(),
                        arrivalDate,
                        departureDate),
                arrivalDate,
                departureDate);
    }


    public void assignRooms(String stayId, Map<String, Set<RoomNumber>> selectedCategories, LocalDate fromDate, LocalDate toDate) {

        // create RoomOccupation for each returned room
        // change state of each returned room
        for (Map.Entry<String, Set<RoomNumber>> category : selectedCategories.entrySet()) {
            category.getValue().forEach((RoomNumber roomNumber) -> {
                System.out.println(category.getKey() + ", " + roomNumber.getNumber());
//                // create roomOccupation
//                roomOccupationRepository.store(new RoomOccupation(new StayId(stayId), roomNumber, fromDate, toDate);
//
//                // change state
//                roomRepository.getRoomByRoomNumber(roomNumber).changeState(RoomState.OCCUPIED);

            });
        }
    }

}
