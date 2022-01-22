package at.fhv.hotelmanagement.application.api;

import at.fhv.hotelmanagement.application.dto.GuestDTO;

import java.util.List;
import java.util.Optional;

public interface GuestService {
    List<GuestDTO> allGuests();

    Optional<GuestDTO> guestByGuestId(String guestId);
}
