package at.fhv.hotelmanagement.view;

import at.fhv.hotelmanagement.application.api.CategoryService;
import at.fhv.hotelmanagement.application.dto.RoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RoomViewController {
    // roomview url
    private static final String ROOM_URL = "/rooms";

    // roomview view
    private static final String ROOM_VIEW = "roomOverview";

    @Autowired
    private CategoryService categoryService;

    @GetMapping(ROOM_URL)
    public String rooms(Model model) {

        // Fetch rooms
        List<RoomDTO> rooms = this.categoryService.allRooms();

        // Attach to view
        model.addAttribute("rooms", rooms);

        return ROOM_VIEW;
    }

}
