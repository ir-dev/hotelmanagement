package at.fhv.hotelmanagement.view;

import at.fhv.hotelmanagement.application.api.BookingsService;
import at.fhv.hotelmanagement.application.api.CategoryService;
import at.fhv.hotelmanagement.application.api.StayService;
import at.fhv.hotelmanagement.application.dto.AvailableCategoryDTO;
import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.StayDTO;
import at.fhv.hotelmanagement.domain.model.CreateGuestException;
import at.fhv.hotelmanagement.domain.model.CreateStayException;
import at.fhv.hotelmanagement.domain.model.InsufficientRoomsException;
import at.fhv.hotelmanagement.view.forms.StayForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static at.fhv.hotelmanagement.view.GenericViewController.redirect;
import static at.fhv.hotelmanagement.view.GenericViewController.redirectError;

@Controller
public class StayViewController {
    // stays urls
    private static final String ALL_STAYS_URL = "/stays";
    private static final String CREATE_STAY_URL = "/checkin";
    private static final String STAY_URL = "/stay";

    // stays views
    private static final String ALL_STAYS_VIEW = "allStays";
    private static final String CREATE_STAY_VIEW = "createStay";
    private static final String STAY_VIEW = "stay";

    // create stay steps
    private static final String CREATE_STAY_STAY_DETAILS_STEP = "enterStayDetails";
    private static final String CREATE_STAY_ROOM_CATEGORIES_STEP = "enterRoomCategories";
    private static final String CREATE_STAY_GUEST_DETAILS_STEP = "enterGuestDetails";
    private static final String CREATE_STAY_PAYMENT_STEP = "enterPayment";
    private static final String CREATE_STAY_SUMMARY_STEP = "confirmSummary";
    private static final String CREATE_STAY_STORE_STEP = "store";
    private static final Set<String> wizardSteps = Set.of(
            CREATE_STAY_STAY_DETAILS_STEP,
            CREATE_STAY_ROOM_CATEGORIES_STEP,
            CREATE_STAY_GUEST_DETAILS_STEP,
            CREATE_STAY_PAYMENT_STEP,
            CREATE_STAY_SUMMARY_STEP,
            CREATE_STAY_STORE_STEP
    );

    @Autowired
    private BookingsService bookingsService;

    @Autowired
    private StayService stayService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(ALL_STAYS_URL)
    public String allStays(
            @RequestParam("msg") Optional<String> msg,
            Model model) {
        final List<StayDTO> stays = this.stayService.allStays();

        if (msg.isPresent()) {
            model.addAttribute("msg", msg);
        }

        model.addAttribute("stays", stays);

        return ALL_STAYS_VIEW;
    }

    @GetMapping(CREATE_STAY_URL)
    public ModelAndView createStayForBookingForm(
            @RequestParam("bookingNo") String bookingNo,
            Model model) {

        final Optional<BookingDTO> optBooking = this.bookingsService.bookingByBookingNo(bookingNo);

        if (optBooking.isEmpty()) {
            return redirectError("Booking with no.: " + bookingNo + " not found");
        }

        StayForm form = new StayForm();
        form.addBooking(optBooking.get());

        model.addAttribute("step", "enterStayDetails");
        model.addAttribute("bookingNo", bookingNo);
        model.addAttribute("form", form);
        model.addAttribute("arrivalDate", LocalDate.now());

        return new ModelAndView(CREATE_STAY_VIEW);
    }

    @PostMapping(CREATE_STAY_URL)
    public ModelAndView createStayForBooking(
            @RequestParam("step") String step,
            @RequestParam("bookingNo") String bookingNo,
            @ModelAttribute StayForm form,
            Model model) {

        final Optional<BookingDTO> optBooking = this.bookingsService.bookingByBookingNo(bookingNo);

        if (optBooking.isEmpty()) {
            return redirectError("Booking with no.: " + bookingNo + " not found");
        }

        form.addBooking(optBooking.get());

        if (!wizardSteps.contains(step)) {
            return redirectError("Invalid step in create stay wizard.");
        }

        switch (step) {
            case CREATE_STAY_STAY_DETAILS_STEP:
                model.addAttribute("arrivalDate", LocalDate.now());
                break;

            case CREATE_STAY_ROOM_CATEGORIES_STEP:
                // Fetch categories in given stay timespan for which rooms are available
                List<AvailableCategoryDTO> availableCategories = this.categoryService.availableCategories(LocalDate.now(), form.getDepartureDate());

                // Attach to "enter room categories" view
                model.addAttribute("categories", availableCategories);
                break;

            case CREATE_STAY_STORE_STEP:
                try {
                    this.stayService.createStayForBooking(bookingNo, form);
                } catch (CreateStayException | CreateGuestException | InsufficientRoomsException e) {
                    return redirectError(e.getMessage());
                }
                return redirect(ALL_STAYS_URL, "Check-In successful");
        }

        model.addAttribute("step", step);
        model.addAttribute("bookingNo", bookingNo);
        model.addAttribute("form", form);

        return new ModelAndView(CREATE_STAY_VIEW);
    }

    @GetMapping(STAY_URL)
    public ModelAndView stay(
            @RequestParam("id") String stayId,
            Model model) {

        final Optional<StayDTO> stay = this.stayService.stayByStayId(stayId);

        if (stay.isEmpty()) {
            return redirectError("Stay with id.: " + stayId + " not found");
        }

        model.addAttribute("stay", stay.get());

        return new ModelAndView(STAY_VIEW);
    }
}
