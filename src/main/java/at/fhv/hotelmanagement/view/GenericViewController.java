package at.fhv.hotelmanagement.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class GenericViewController {
    // generic urls
    private static final String ERROR_URL = "/displayerror";

    // generic views
    private static final String ERROR_VIEW = "errorView";

    @GetMapping(ERROR_URL)
    public String displayError(@RequestParam("msg") String msg, Model model) {
        model.addAttribute("msg", msg);

        return ERROR_VIEW;
    }

    public static ModelAndView redirectError(String msg) {
        return redirect(GenericViewController.ERROR_URL, msg);
    }

    public static ModelAndView redirect(String URL, String msg) {
        return new ModelAndView("redirect:" + URL  + "?msg=" + msg);
    }

    public static ModelAndView redirect(String URL, Map<String, String> params) {
        StringBuilder redirectStr = new StringBuilder("redirect:" + URL);

        if (params.size() > 0) {
            redirectStr.append("?");

            for (Map.Entry<String, String> param : params.entrySet()) {
                redirectStr
                        .append(param.getKey())
                        .append("=")
                        .append(param.getValue());
            }
        }

        return new ModelAndView(redirectStr.toString());
    }

    public static ModelAndView redirect(String URL) {
        return new ModelAndView("redirect:" + URL);
    }
}
