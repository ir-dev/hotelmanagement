package at.fhv.hotelmanagement.view;

import at.fhv.hotelmanagement.application.api.BookingsService;
import at.fhv.hotelmanagement.application.api.StayService;
import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.StayDTO;
import at.fhv.hotelmanagement.application.impl.CreateStayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    private BookingsService bookingsService;

    @Autowired
    private StayService stayService;

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
    public ModelAndView createAndCheckinStayForBookingForm(
            @RequestParam("bookingNo") String bookingNo,
            Model model) {
        final Optional<BookingDTO> booking = this.bookingsService.bookingByBookingNo(bookingNo);

        if (booking.isEmpty()) {
            return redirectError("Booking with no.: " + bookingNo + " not found");
        }

        model.addAttribute("booking", booking.get());
        return new ModelAndView(CREATE_STAY_VIEW);
    }

    @PostMapping(CREATE_STAY_URL)
    public ModelAndView createAndCheckinStayForBooking(
            @RequestParam("bookingNo") String bookingNo,
            Model model) {

        try {
            this.stayService.createAndCheckinStayForBooking(bookingNo);
        } catch (CreateStayException e) {
            return redirectError(e.getMessage());
        }

        return redirect(ALL_STAYS_URL, "Check-In successful");
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
