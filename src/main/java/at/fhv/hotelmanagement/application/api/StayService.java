package at.fhv.hotelmanagement.application.api;

import at.fhv.hotelmanagement.application.dto.StayDTO;
import at.fhv.hotelmanagement.application.impl.CreateStayException;
import at.fhv.hotelmanagement.view.forms.StayForm;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public interface StayService {
    List<StayDTO> allStays();

    Optional<StayDTO> stayByStayId(String stayId);

    void createStayForBooking(String bookingNo, StayForm form) throws CreateStayException;

    void assignRooms(String stayId, Map<String, Integer> selectedCategories, LocalDate fromDate, LocalDate toDate) throws CreateStayException;
}
