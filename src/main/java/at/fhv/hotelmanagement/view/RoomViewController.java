package at.fhv.hotelmanagement.view;

import at.fhv.hotelmanagement.application.api.CategoryService;
import at.fhv.hotelmanagement.application.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static at.fhv.hotelmanagement.view.GenericViewController.redirect;
import static at.fhv.hotelmanagement.view.GenericViewController.redirectError;

@Controller
public class RoomViewController {
    // roomview url
    private static final String ALL_ROOMS_URL = "/rooms";
    private static final String ROOM_URL = "/room";

    // roomview view
    private static final String ROOMS_VIEW = "roomOverview";

    @Autowired
    private CategoryService categoryService;

    @GetMapping(ALL_ROOMS_URL)
    public String rooms(Model model) {

        // Fetch rooms
        List<CategoryDTO> categories = this.categoryService.allCategories();

        // Attach to view
        model.addAttribute("categories", categories);

        return ROOMS_VIEW;
    }

    @PostMapping(ROOM_URL)
    public ModelAndView room(
            @RequestParam("category") String category,
            @RequestParam("number") String number,
            @RequestParam("state") String state) {

        try {
            this.categoryService.manageCategory(category, number, state);
        } catch (IllegalArgumentException e) {
            return redirectError(e.getMessage());
        }
        return redirect(ALL_ROOMS_URL);
    }

}
