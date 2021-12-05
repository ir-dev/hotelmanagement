package at.fhv.hotelmanagement.application.api;

import at.fhv.hotelmanagement.application.dto.InvoiceDTO;
import at.fhv.hotelmanagement.application.dto.StayDTO;

import at.fhv.hotelmanagement.domain.model.CreateStayException;
import at.fhv.hotelmanagement.domain.model.InsufficientRoomsException;
import at.fhv.hotelmanagement.domain.model.CreateGuestException;
import at.fhv.hotelmanagement.domain.model.RoomAssignmentException;

import at.fhv.hotelmanagement.view.forms.StayForm;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public interface StayService {
    List<StayDTO> allStays();

    Optional<StayDTO> stayByStayId(String stayId);

    Optional<InvoiceDTO> viewChargeStay(String stayId);

    Optional<InvoiceDTO> chargeStay(String stayId);

    void createStayForBooking(String bookingNo, StayForm form) throws CreateStayException, CreateGuestException, RoomAssignmentException;

    void createStayForWalkIn(StayForm form) throws CreateStayException, CreateGuestException, RoomAssignmentException;

}
