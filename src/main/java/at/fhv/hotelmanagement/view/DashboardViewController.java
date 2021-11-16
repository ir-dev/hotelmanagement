package at.fhv.hotelmanagement.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardViewController {
    // dashboard urls
    private static final String DASHBOARD_URL = "/";

    // dashboard views
    private static final String DASHBOARD_VIEW = "dashboard";

    @GetMapping(DASHBOARD_URL)
    public String dashboard() {
        return DASHBOARD_VIEW;
    }
}
