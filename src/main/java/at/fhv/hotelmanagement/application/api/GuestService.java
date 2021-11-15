package at.fhv.hotelmanagement.application.api;

import at.fhv.hotelmanagement.application.dto.GuestDTO;
import at.fhv.hotelmanagement.domain.model.GuestId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface GuestService {
    List<GuestDTO> allGuests();

    Optional<GuestDTO> guestByGuestId(String guestId);
}
