package at.fhv.hotelmanagement.view;

import at.fhv.hotelmanagement.application.api.BookingsService;
import at.fhv.hotelmanagement.application.api.CategoryService;
import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.BookingDetailsDTO;
import at.fhv.hotelmanagement.application.dto.AvailableCategoryDTO;
import at.fhv.hotelmanagement.domain.model.booking.CreateBookingException;
import at.fhv.hotelmanagement.domain.model.guest.CreateGuestException;
import at.fhv.hotelmanagement.view.forms.BookingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static at.fhv.hotelmanagement.view.GenericViewController.redirect;
import static at.fhv.hotelmanagement.view.GenericViewController.redirectError;

@Controller
public class BookingViewController {
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
    public String allBookings(
            @RequestParam("msg") Optional<String> msg,
            Model model) {
        final List<BookingDTO> bookings = this.bookingsService.allBookings();

        if (msg.isPresent()) {
            model.addAttribute("msg", msg);
        }

        model.addAttribute("bookings", bookings);

        return ALL_BOOKINGS_VIEW;
    }

    @GetMapping(CREATE_BOOKING_URL)
    public String createBookingForm(Model model) {
        model.addAttribute("step", "enterStayDetails");
        model.addAttribute("form", new BookingForm());

        return CREATE_BOOKING_VIEW;
    }

    @PostMapping(CREATE_BOOKING_URL)
    public ModelAndView createBooking(
            @RequestParam("step") String step,
            @ModelAttribute BookingForm form,
            Model model) {

        if (!wizardSteps.contains(step)) {
            return redirectError("Invalid step in create booking wizard.");
        }

        if (step.equals(CREATE_BOOKING_ROOM_CATEGORIES_STEP)) {
            // Fetch categories in given timespan for which a booking is possible
            List<AvailableCategoryDTO> availableCategories = this.categoryService.availableCategoriesForBooking(form.getArrivalDate(), form.getDepartureDate());

            // Attach to "enter room categories" view
            model.addAttribute("categories", availableCategories);
        }

        if (step.equals(CREATE_BOOKING_STORE_STEP)) {
            try {
                this.bookingsService.createBooking(form);
            } catch (CreateBookingException | CreateGuestException e) {
                return redirectError(e.getMessage());
            }

            return redirect(ALL_BOOKINGS_URL, "Booking successfully created");
        }

        model.addAttribute("step", step);
        model.addAttribute("form", form);

        return new ModelAndView(CREATE_BOOKING_VIEW);
    }

    @GetMapping(BOOKING_URL)
    public ModelAndView booking(
            @RequestParam("no") String bookingNo,
            Model model) {
        final Optional<BookingDTO> booking = this.bookingsService.bookingByBookingNo(bookingNo);
        final Optional<BookingDetailsDTO> bookingDetail = this.bookingsService.bookingDetailsByBookingNo(bookingNo);

        if (booking.isEmpty() || bookingDetail.isEmpty()){
            return redirectError("Booking with no.: " + bookingNo + " not found");
        }

        model.addAttribute("booking", booking.get());
        model.addAttribute("bookingDetail", bookingDetail.get());
        model.addAttribute("guest", bookingDetail.get().guest());

        return new ModelAndView(BOOKING_VIEW);
    }
}
