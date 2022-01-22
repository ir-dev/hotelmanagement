package at.fhv.hotelmanagement.view.rest;

import at.fhv.hotelmanagement.application.api.CategoryService;
import at.fhv.hotelmanagement.application.dto.AvailableCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rest")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081" } )
public class CategoryRestController {
    // categories urls
    private static final String CATEGORIES_URL = "/categories";

    @Autowired
    private CategoryService categoryService;

    @GetMapping(CATEGORIES_URL)
    @ResponseBody
    public List<AvailableCategoryDTO> availableCategoriesForBooking(
            @RequestParam("arrivalDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate arrivalDate,
            @RequestParam("departureDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate departureDate) {
        return this.categoryService.availableCategoriesForBooking(arrivalDate, departureDate);
    }
}
