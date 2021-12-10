package at.fhv.hotelmanagement.application.api;

import at.fhv.hotelmanagement.application.dto.InvoiceDTO;
import at.fhv.hotelmanagement.application.dto.StayDTO;

import at.fhv.hotelmanagement.application.impl.EntityNotFoundException;
import at.fhv.hotelmanagement.domain.model.PriceCurrencyMismatchException;
import at.fhv.hotelmanagement.domain.model.stay.CreateStayException;
import at.fhv.hotelmanagement.domain.model.guest.CreateGuestException;
import at.fhv.hotelmanagement.domain.model.category.RoomAssignmentException;
import at.fhv.hotelmanagement.domain.model.stay.BillingOpenException;
import at.fhv.hotelmanagement.view.forms.StayForm;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public interface StayService {
    List<StayDTO> allStays();

    Optional<StayDTO> stayByStayId(String stayId);

    Optional<InvoiceDTO> invoiceByInvoiceNo(String invoiceNo);

    List<InvoiceDTO> allStayInvoices(String stayId) throws EntityNotFoundException;

    InvoiceDTO chargeStayPreview(String stayId) throws EntityNotFoundException, PriceCurrencyMismatchException;

    String chargeStay(String stayId) throws EntityNotFoundException, PriceCurrencyMismatchException, IllegalStateException;

    void checkoutStay(String stayId) throws EntityNotFoundException, BillingOpenException, IllegalStateException;

    void createStayForBooking(String bookingNo, StayForm form) throws CreateStayException, CreateGuestException, RoomAssignmentException;

    void createStayForWalkIn(StayForm form) throws CreateStayException, CreateGuestException, RoomAssignmentException;

}
