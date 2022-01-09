package at.fhv.hotelmanagement.domain.model.category;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.stay.StayId;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CategoryServiceTest extends AbstractTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    void given_category_when_autoassignrooms_then_throwsanddoesnotthrow() throws RoomAlreadyExistsException {
        // given
        List<Category> categories = createCategoriesDummy();
        Mockito.when(this.categoryRepository.findAll()).thenReturn(categories);
        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        selectedCategoriesRoomCount.put(categories.get(0), 2);

        // when..then
        assertDoesNotThrow(() -> this.categoryService.autoAssignRooms(selectedCategoriesRoomCount, LocalDate.now(), LocalDate.now().plusDays(3), new StayId("1")));
        assertThrows(RoomAssignmentException.class, () -> this.categoryService.autoAssignRooms(selectedCategoriesRoomCount, LocalDate.now(), LocalDate.now().plusDays(3), new StayId("1")));
    }

    @Test
    void given_category_when_autoassignrooms_then_roomsassigned() throws RoomAlreadyExistsException, RoomAssignmentException {
        // given
        List<Category> categories = createCategoriesDummy();
        Mockito.when(this.categoryRepository.findAll()).thenReturn(categories);
        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        selectedCategoriesRoomCount.put(categories.get(0), 2);

        // when
        this.categoryService.autoAssignRooms(selectedCategoriesRoomCount, LocalDate.now(), LocalDate.now().plusDays(3), new StayId("1"));

        // then
        assertTrue(categories.get(0).getAvailableRoomNumbers(LocalDate.now(), LocalDate.now().plusDays(3)).isEmpty());
    }

    @Test
    void given_category_when_releaserooms_then_roomsavailable() throws RoomAlreadyExistsException, RoomAssignmentException {
        // given
        List<Category> categories = createCategoriesDummy();
        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        selectedCategoriesRoomCount.put(categories.get(0), 2);
        this.categoryService.autoAssignRooms(selectedCategoriesRoomCount, LocalDate.now(), LocalDate.now().plusDays(3), new StayId("1"));

        // when..then
        assertDoesNotThrow(() ->
                this.categoryService.releaseRooms(
                        List.of(new RoomNumber("100")),
                        new HashSet<>(categories),
                        new StayId("1")));

        assertThrows(IllegalArgumentException.class, () ->
                this.categoryService.releaseRooms(
                        List.of(new RoomNumber("101"), new RoomNumber("102")),
                        new HashSet<>(categories),
                        new StayId("1")));
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
