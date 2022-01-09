package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.application.api.CategoryService;
import at.fhv.hotelmanagement.application.dto.AvailableCategoryDTO;
import at.fhv.hotelmanagement.application.dto.CategoryDTO;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.category.CategoryFactory;
import at.fhv.hotelmanagement.domain.model.category.CategoryId;
import at.fhv.hotelmanagement.domain.model.category.RoomAlreadyExistsException;
import at.fhv.hotelmanagement.domain.model.category.Room;
import at.fhv.hotelmanagement.domain.model.category.RoomNumber;
import at.fhv.hotelmanagement.domain.model.category.RoomState;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CategoryServiceImplTest extends AbstractTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    void given_emptyrepository_when_fetchallavailablecategories_then_empty() {
        //given
        Mockito.when(this.categoryRepository.findAll()).thenReturn(Collections.emptyList());
        //when
        List<AvailableCategoryDTO> categoriesDto = this.categoryService.availableCategories(LocalDate.now(), LocalDate.now().plusDays(2));
        //then
        assertTrue(categoriesDto.isEmpty());
    }

    @Test
    void given_emptyrepository_when_fetchallcategories_then_empty() {
        //given
        Mockito.when(this.categoryRepository.findAll()).thenReturn(Collections.emptyList());
        //when
        List<CategoryDTO> categoriesDto = this.categoryService.allCategories();
        //then
        assertTrue(categoriesDto.isEmpty());
    }

    @Test
    void given_2availablecategories_when_fetchavailablecategories_then_returnequalcategories() throws RoomAlreadyExistsException {
        //given
        List<Category> categories = createCategoriesDummy();
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

    @Test
    void given_2categories_when_fetchallcategories_then_returnequalcategories() throws RoomAlreadyExistsException {
        //given
        List<Category> categories = createCategoriesDummy();
        Mockito.when(this.categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDTO> categoriesDtoExpected = new ArrayList<>();
        for (Category c : categories) {
            CategoryDTO categoryDto = CategoryDTO.builder()
                    .withName(c.getName())
                    .withRooms(c.getAllRoomNumbersWithRoomStates())
                    .build();
            categoriesDtoExpected.add(categoryDto);
        }

        //when
        List<CategoryDTO> categoriesDtoActual = this.categoryService.allCategories();

        //then
        for (CategoryDTO c : categoriesDtoActual) {
            assertTrue(categoriesDtoExpected.contains(c));
        }
    }

    @Test
    void given_category_without_availablerooms_when_fetchavailablecategories_then_returnempty() {
        //given
        Price p1 = Price.of(BigDecimal.valueOf(150), Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(new CategoryId("1"), "Business Casual EZ", "A casual accommodation for business guests.", 1, p1, p1);
        List<Category> categories = List.of(category);
        Mockito.when(this.categoryRepository.findAll()).thenReturn(categories);

        //when
        List<AvailableCategoryDTO> availableCategoriesDto = this.categoryService.availableCategories(LocalDate.now(), LocalDate.now().plusDays(2));

        //then
        assertTrue(availableCategoriesDto.isEmpty());
    }

    @Test
    void given_category_when_managecategory_then_throwsanddoesnotthrow() throws IllegalArgumentException, RoomAlreadyExistsException {
        //given
        List<Category> categories = createCategoriesDummy();

        // when.category not found.then
        Mockito.when(this.categoryRepository.findByName("noCategory")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> this.categoryService.manageCategory("noCategory", categories.get(0).getRoomByRoomNumber(new RoomNumber("100")).getRoomNumber().getNumber(), "AVAILABLE"));

        // when.room not found.then
        Mockito.when(this.categoryRepository.findByName(categories.get(0).getName())).thenReturn(Optional.of(categories.get(0)));
        assertThrows(IllegalArgumentException.class, () -> this.categoryService.manageCategory(categories.get(0).getName(), "0", "CLEANING"));

        // when.everything is fine.then
        Mockito.when(this.categoryRepository.findByName(categories.get(0).getName())).thenReturn(Optional.of(categories.get(0)));
        assertDoesNotThrow(() -> this.categoryService.manageCategory(categories.get(0).getName(), categories.get(0).getRoomByRoomNumber(new RoomNumber("100")).getRoomNumber().getNumber(), "CLEANING"));
    }

    private List<Category> createCategoriesDummy() throws RoomAlreadyExistsException {
        Price p1 = Price.of(BigDecimal.valueOf(150), Currency.getInstance("EUR"));
        Category category1 = CategoryFactory.createCategory(new CategoryId("1"), "Business Casual EZ", "A casual accommodation for business guests.", 1, p1, p1);
        category1.createRoom(new Room(new RoomNumber("100"), RoomState.AVAILABLE));
        category1.createRoom(new Room(new RoomNumber("101"), RoomState.AVAILABLE));

        Price p2 = Price.of(BigDecimal.valueOf(200), Currency.getInstance("EUR"));
        Category category2 = CategoryFactory.createCategory(new CategoryId("2"), "Business Casual DZ", "A casual accommodation for business guests.", 2, p2, p2);
        category2.createRoom(new Room(new RoomNumber("200"), RoomState.AVAILABLE));
        category2.createRoom(new Room(new RoomNumber("201"), RoomState.AVAILABLE));

        return Arrays.asList(category1, category2);
    }
}