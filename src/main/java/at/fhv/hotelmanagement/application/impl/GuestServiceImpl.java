package at.fhv.hotelmanagement.application.impl;


import at.fhv.hotelmanagement.application.api.GuestService;
import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.GuestDTO;
import at.fhv.hotelmanagement.domain.model.Booking;
import at.fhv.hotelmanagement.domain.model.Guest;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class GuestServiceImpl implements GuestService {

    @Autowired
    GuestRepository guestRepository;

    @Override
    public List<GuestDTO> getAll() {
        List<Guest> guests = guestRepository.getAll();

        List<GuestDTO> guestsDto = new ArrayList<>();
        for(Guest g: guests){
            guestsDto.add(GuestDTO.builder().withGuestEntity(g).build());
        }

       return guestsDto;
    }

    @Override
    public Optional<GuestDTO> getById(String guestId) {
        Optional<Guest> g = guestRepository.findById(guestId);
        if(g.isEmpty()){
            return Optional.empty();
        }
        Guest guest = g.get();

        return Optional.of(GuestDTO.builder()
                .withGuestEntity(guest)
                .build());
    }
}
