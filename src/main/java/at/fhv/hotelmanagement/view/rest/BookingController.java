package at.fhv.hotelmanagement.view.rest;

import at.fhv.hotelmanagement.application.api.BookingsService;
import at.fhv.hotelmanagement.application.api.CategoryService;
import at.fhv.hotelmanagement.application.dto.AvailableCategoryDTO;
import at.fhv.hotelmanagement.domain.model.booking.CreateBookingException;
import at.fhv.hotelmanagement.domain.model.guest.CreateGuestException;
import at.fhv.hotelmanagement.view.forms.BookingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class BookingController {
    // bookings urls
    private static final String CATEGORIES_URL = "/categories";
    private static final String CREATE_BOOKING_URL = "/bookings/create";

    @Autowired
    private BookingsService bookingsService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(CATEGORIES_URL)
    @ResponseBody
    public List<AvailableCategoryDTO> categories(
            @RequestParam("arrivalDate") LocalDate arrivalDate,
            @RequestParam("departureDate") LocalDate departureDate) {
        return this.categoryService.availableCategories(arrivalDate, departureDate);
    }

    @PostMapping(CREATE_BOOKING_URL)
    @ResponseBody
    public CreateBookingResponse createBooking(
            @RequestBody BookingForm form) {
        try {
            this.bookingsService.createBooking(form);
            return CreateBookingResponse.success();
        } catch (CreateBookingException | CreateGuestException e) {
            return CreateBookingResponse.error(e);
        }
    }
}
