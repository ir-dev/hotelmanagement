package at.fhv.hotelmanagement.view.rest;

import at.fhv.hotelmanagement.application.api.BookingsService;
import at.fhv.hotelmanagement.domain.model.booking.CreateBookingException;
import at.fhv.hotelmanagement.domain.model.guest.CreateGuestException;
import at.fhv.hotelmanagement.view.forms.BookingForm;
import at.fhv.hotelmanagement.view.rest.responses.BookingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081" } )
public class BookingRestController {
    // bookings urls
    private static final String CREATE_BOOKING_URL = "/bookings/create";

    @Autowired
    private BookingsService bookingsService;

    @PostMapping(CREATE_BOOKING_URL)
    @ResponseBody
    public BookingResponse createBooking(
            @RequestBody BookingForm form) {
        try {
            this.bookingsService.createBooking(form);
            return BookingResponse.success();
        } catch (CreateBookingException | CreateGuestException e) {
            return BookingResponse.error(e);
        }
    }
}
