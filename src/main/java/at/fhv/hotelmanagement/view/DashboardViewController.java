package at.fhv.hotelmanagement.view;

import at.fhv.hotelmanagement.application.api.BookingsService;
import at.fhv.hotelmanagement.application.dto.BookingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class DashboardViewController {
    // dashboard urls
    private static final String DASHBOARD_URL = "/";

    // dashboard views
    private static final String DASHBOARD_VIEW = "dashboard";

    @Autowired
    private BookingsService bookingsService;

    @GetMapping(DASHBOARD_URL)
    public String dashboard(Model model) {

            final List<BookingDTO> bookings = this.bookingsService.allBookings();
            model.addAttribute("bookings", bookings);
            model.addAttribute("localDate", LocalDate.now());

        return DASHBOARD_VIEW;
    }


}
