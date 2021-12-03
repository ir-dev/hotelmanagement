package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.CategoryService;
import at.fhv.hotelmanagement.application.dto.AvailableCategoryDTO;
import at.fhv.hotelmanagement.domain.infrastructure.HibernateCategoryRepository;
import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.category.CategoryFactory;
import at.fhv.hotelmanagement.domain.model.category.CategoryId;
import at.fhv.hotelmanagement.domain.model.category.AlreadyExistsException;
import at.fhv.hotelmanagement.domain.model.category.Room;
import at.fhv.hotelmanagement.domain.model.category.RoomNumber;
import at.fhv.hotelmanagement.domain.model.category.RoomState;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private HibernateCategoryRepository categoryRepository;

    @Test
    void given_emptyrepository_when_fetchingallbookings_then_empty() {
        //given
        Mockito.when(this.categoryRepository.findAll()).thenReturn(Collections.emptyList());
        //when
        List<AvailableCategoryDTO> bookingsDto = this.categoryService.availableCategories(LocalDate.now(), LocalDate.now().plusDays(2));
        //then
        assertTrue(bookingsDto.isEmpty());
    }

    @Test
    void given_2availablecategories_when_fetchallavailablecategories_then_returnequalcategories() throws AlreadyExistsException {
        //given
        Category category1 = CategoryFactory.createCategory(new CategoryId("1"),"Business Casual EZ", "A casual accomodation for business guests", 1);
        Category category2 = CategoryFactory.createCategory(new CategoryId("2"),"Business Casual DZ", "A casual accomodation for business guests", 2);
        category1.createRoom(new Room(new RoomNumber("100"), RoomState.AVAILABLE));
        category2.createRoom(new Room(new RoomNumber("200"), RoomState.AVAILABLE));

        List<Category> categories = Arrays.asList(category1, category2);
        Mockito.when(this.categoryRepository.findAll()).thenReturn(categories);

        List<AvailableCategoryDTO> availableCategoriesDtoExpected = new ArrayList<>();
        for (Category c : categories) {
            AvailableCategoryDTO availableCategoryDto = AvailableCategoryDTO.builder()
                    .withName(c.getName())
                    .withDescription(c.getDescription())
                    .withAvailableRoomsCount(c.getAvailableRoomsCount(LocalDate.now(), LocalDate.now().plusDays(2)))
                    .build();
            availableCategoriesDtoExpected.add(availableCategoryDto);
        }

        //when
        List<AvailableCategoryDTO> availableCategoriesDtoActual = this.categoryService.availableCategories(LocalDate.now(), LocalDate.now().plusDays(2));

        //then
        for (AvailableCategoryDTO c : availableCategoriesDtoActual) {
            assertTrue(availableCategoriesDtoExpected.contains(c));
        }
    }
}