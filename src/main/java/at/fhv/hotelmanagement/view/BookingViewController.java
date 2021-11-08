package at.fhv.hotelmanagement.view;

import at.fhv.hotelmanagement.application.api.BookingsService;
import at.fhv.hotelmanagement.application.api.CategoryService;
import at.fhv.hotelmanagement.application.api.GuestService;
import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.BookingDetailsDTO;
import at.fhv.hotelmanagement.application.dto.AvailableCategoryDTO;
import at.fhv.hotelmanagement.view.forms.BookingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Controller
public class BookingViewController {
    // bookings urls
    private static final String ALL_BOOKINGS_URL = "/bookings";
    private static final String CREATE_BOOKING_URL = "/bookings/create";
    private static final String BOOKING_URL = "/booking";
    // generic urls
    private static final String ERROR_URL = "/displayerror";

    // bookings views
    private static final String ALL_BOOKINGS_VIEW = "allBookings";
    private static final String CREATE_BOOKING_VIEW = "createBooking";
    private static final String BOOKING_VIEW = "booking";
    // generic views
    private static final String ERROR_VIEW = "errorView";

    @Autowired
    private BookingsService bookingsService;

    @Autowired
    private GuestService guestService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(ALL_BOOKINGS_URL)
    public String allBookings(Model model) {
        final List<BookingDTO> bookings = bookingsService.allBookings();

        model.addAttribute("bookings", bookings);

        return ALL_BOOKINGS_VIEW;
    }

    @GetMapping(CREATE_BOOKING_URL)
    public String createBooking(Model model) {
        model.addAttribute("step", "enterStayDetails");

        return CREATE_BOOKING_VIEW;
    }

    @PostMapping(CREATE_BOOKING_URL)
    public String createBookingWizard(
            @RequestParam("step") String step,
            @ModelAttribute BookingForm form,
            Model model) {

        final Set<String> wizardSteps = Set.of(
                "enterStayDetails",
                "enterRoomCategories",
                "enterGuestDetails",
                "enterPayment",
                "confirmSummary",
                "storeBooking"
        );

        if (!wizardSteps.contains(step)) {
            redirectError("Invalid state in create booking wizard.");
        }

        if (step.equals("enterRoomCategories")) {
            // Fetch categories in given stay timespan for which rooms are available
            List<AvailableCategoryDTO> availableCategories = categoryService.availableCategories(form.getArrivalDateValue(), form.getDepartureDateValue());

            // Attach to "enter room categories" view
            model.addAttribute("categories", availableCategories);
        }

        if (step.equals("storeBooking")) {
            bookingsService.createBooking(form);
            redirect(ALL_BOOKINGS_URL);
        }

        model.addAttribute("step", step);
        model.addAttribute("form", form);

        return CREATE_BOOKING_VIEW;
    }

    @GetMapping(BOOKING_URL)
    public ModelAndView booking(
            @RequestParam("id") String bookingNo,
            Model model) {
        final Optional<BookingDTO> booking = bookingsService.bookingByBookingNo(bookingNo);
        final Optional<BookingDetailsDTO> bookingDetail = bookingsService.bookingDetailsByBookingNo(bookingNo);
        if(booking.isEmpty() || bookingDetail.isEmpty()){
            redirectError("Booking with no.: " + bookingNo + " not found");
        }

        model.addAttribute("booking", booking.get());
        model.addAttribute("bookingDetail", bookingDetail.get());
        model.addAttribute("guest", bookingDetail.get().guest());

        return new ModelAndView(BOOKING_VIEW);
    }

    private static ModelAndView redirect(String URL) {
        return new ModelAndView("redirect:" + URL);
    }

    @GetMapping(ERROR_URL)
    public String displayError(@RequestParam("msg") String msg, Model model) {
        model.addAttribute("msg", msg);

        return ERROR_VIEW;
    }

    @SuppressWarnings("unused")
    private static ModelAndView redirectError(String msg) {
        return new ModelAndView("redirect:" + ERROR_URL + "?msg=" + msg);
    }
}
