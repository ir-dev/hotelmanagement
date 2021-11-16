package at.fhv.hotelmanagement.application.api;

import at.fhv.hotelmanagement.application.dto.StayDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface StayService {
    List<StayDTO> allStays();

    Optional<StayDTO> stayByStayId(String stayId);

    void createAndCheckinStayForBooking(String bookingNo);
}
