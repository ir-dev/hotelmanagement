package at.fhv.hotelmanagement.application.api;

import at.fhv.hotelmanagement.application.dto.StayDTO;
import at.fhv.hotelmanagement.domain.model.RoomNumber;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public interface StayService {
    List<StayDTO> allStays();

    Optional<StayDTO> stayByStayId(String stayId);

    void createAndCheckinStayForBooking(String bookingNo);

    void assignRooms(String stayId, Map<String, Set<RoomNumber>> selectedCategories, LocalDate fromDate, LocalDate toDate);
}
