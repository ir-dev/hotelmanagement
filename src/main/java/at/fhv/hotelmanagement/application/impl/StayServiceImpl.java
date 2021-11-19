package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.StayService;
import at.fhv.hotelmanagement.application.dto.GuestDTO;
import at.fhv.hotelmanagement.application.dto.StayDTO;
import at.fhv.hotelmanagement.domain.model.*;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import at.fhv.hotelmanagement.domain.repositories.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class StayServiceImpl implements StayService {
    @Autowired
    StayRepository stayRepository;

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

        // TODO: Set BookingStatus to CLOSED

        stayRepository.store(stay);
    }
}
