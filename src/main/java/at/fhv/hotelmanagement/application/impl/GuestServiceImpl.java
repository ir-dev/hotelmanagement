package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.GuestService;
import at.fhv.hotelmanagement.application.dto.GuestDTO;
import at.fhv.hotelmanagement.domain.model.guest.Guest;
import at.fhv.hotelmanagement.domain.model.guest.GuestId;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GuestServiceImpl implements GuestService {
    @Autowired
    GuestRepository guestRepository;

    @Transactional(readOnly = true)
    @Override
    public List<GuestDTO> allGuests() {
        List<Guest> guests = this.guestRepository.findAll();
        List<GuestDTO> guestsDto = new ArrayList<>();

        for (Guest guest : guests) {
            guestsDto.add(buildGuestDto(guest));
        }

        return guestsDto;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<GuestDTO> guestByGuestId(String guestId) {
        Optional<Guest> guest = this.guestRepository.findById(new GuestId(guestId));
        if (guest.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(buildGuestDto(guest.get()));
    }

    private GuestDTO buildGuestDto(Guest guest) {
        return GuestDTO.builder()
                .withGuestEntity(guest)
                .build();
    }
}
