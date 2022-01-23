package at.fhv.hotelmanagement.view.rest;

import at.fhv.hotelmanagement.application.api.CategoryService;
import at.fhv.hotelmanagement.application.dto.AvailableCategoryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rest")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081"} )
public class CategoryRestController {
    // categories urls
    private static final String CATEGORIES_URL = "/categories";

    @Autowired
    private CategoryService categoryService;

    @GetMapping(CATEGORIES_URL)
    @ResponseBody
    public List<AvailableCategoryDTO> availableCategoriesForBooking(
            @RequestParam("arrivalDate") @Schema(type = "string") String arrivalDate,
            @RequestParam("departureDate") @Schema(type = "string") String departureDate) {
        return this.categoryService.availableCategoriesForBooking(LocalDate.parse(arrivalDate), LocalDate.parse(departureDate));
    }
}
