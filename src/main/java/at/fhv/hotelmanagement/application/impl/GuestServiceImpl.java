package at.fhv.hotelmanagement.application.impl;


import at.fhv.hotelmanagement.application.api.GuestService;
import at.fhv.hotelmanagement.application.dto.GuestDTO;
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
    public List<GuestDTO> allGuests() {
        List<Guest> guests = guestRepository.findAll();
        List<GuestDTO> guestsDto = new ArrayList<>();

        for (Guest guest : guests) {
            guestsDto.add(GuestDTO.builder()
                    .withGuestEntity(guest)
                    .build());
        }

        return guestsDto;
    }

    @Override
    public Optional<GuestDTO> guestByGuestId(String guestId) {
        Optional<Guest> guest = guestRepository.findById(guestId);
        if (guest.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(GuestDTO.builder()
                .withGuestEntity(guest.get())
                .build());
    }
}
