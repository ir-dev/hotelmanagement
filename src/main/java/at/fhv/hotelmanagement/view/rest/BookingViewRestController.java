package at.fhv.hotelmanagement.view.rest;

import at.fhv.hotelmanagement.application.api.BookingsService;
import at.fhv.hotelmanagement.application.api.CategoryService;
import at.fhv.hotelmanagement.application.dto.AvailableCategoryDTO;
import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.BookingDetailsDTO;
import at.fhv.hotelmanagement.domain.model.booking.CreateBookingException;
import at.fhv.hotelmanagement.domain.model.guest.CreateGuestException;
import at.fhv.hotelmanagement.view.forms.BookingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static at.fhv.hotelmanagement.view.GenericViewController.redirect;
import static at.fhv.hotelmanagement.view.GenericViewController.redirectError;

@RestController
@RequestMapping("/rest")
public class BookingViewRestController {
    // bookings urls
    private static final String ALL_BOOKINGS_URL = "/bookings";
    private static final String CREATE_BOOKING_URL = "/bookings/create";
    private static final String BOOKING_URL = "/booking";

    // bookings views
    private static final String ALL_BOOKINGS_VIEW = "allBookings";
    private static final String CREATE_BOOKING_VIEW = "createBooking";
    private static final String BOOKING_VIEW = "booking";

    // create booking steps
    private static final String CREATE_BOOKING_STAY_DETAILS_STEP = "enterStayDetails";
    private static final String CREATE_BOOKING_ROOM_CATEGORIES_STEP = "enterRoomCategories";
    private static final String CREATE_BOOKING_GUEST_DETAILS_STEP = "enterGuestDetails";
    private static final String CREATE_BOOKING_PAYMENT_STEP = "enterPayment";
    private static final String CREATE_BOOKING_SUMMARY_STEP = "confirmSummary";
    private static final String CREATE_BOOKING_STORE_STEP = "store";
    private static final Set<String> wizardSteps = Set.of(
            CREATE_BOOKING_STAY_DETAILS_STEP,
            CREATE_BOOKING_ROOM_CATEGORIES_STEP,
            CREATE_BOOKING_GUEST_DETAILS_STEP,
            CREATE_BOOKING_PAYMENT_STEP,
            CREATE_BOOKING_SUMMARY_STEP,
            CREATE_BOOKING_STORE_STEP
    );

    @Autowired
    private BookingsService bookingsService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(ALL_BOOKINGS_URL)
    @ResponseBody
    public BookingDTO[] allBookings() {
        return this.bookingsService.allBookings().toArray(new BookingDTO[0]);
    }

    @PostMapping(CREATE_BOOKING_URL)
    public CreateBookingResponse createBooking(@ModelAttribute BookingForm form) {
        try {
            this.bookingsService.createBooking(form);
            return CreateBookingResponse.success();
        } catch (CreateBookingException | CreateGuestException e) {
            return CreateBookingResponse.error(e);
        }
    }

    @GetMapping(BOOKING_URL)
    @ResponseBody
    public BookingDTO booking(@RequestParam("no") String bookingNo) {
        return this.bookingsService.bookingByBookingNo(bookingNo).orElseThrow();
    }
}
