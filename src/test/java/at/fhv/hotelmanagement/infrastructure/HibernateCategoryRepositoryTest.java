package at.fhv.hotelmanagement.infrastructure;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.category.RoomAlreadyExistsException;
import at.fhv.hotelmanagement.domain.model.category.*;
import at.fhv.hotelmanagement.domain.model.category.RoomState;
import at.fhv.hotelmanagement.domain.model.stay.StayId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class HibernateCategoryRepositoryTest extends AbstractTest {

    @Autowired
    private HibernateCategoryRepository categoryRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    void given_3categoriesinrepository_when_findallcategories_then_returnequalscategories() {
        // given
        Price p = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        List<Category> categoriesExpected = Arrays.asList(
                CategoryFactory.createCategory(this.categoryRepository.nextIdentity(), "Business Casual EZ", "Casual accommodation for 1 person.", 1, p, p),
                CategoryFactory.createCategory(this.categoryRepository.nextIdentity(), "Business Casual DZ", "Casual accommodation for 2 persons.", 2, p, p),
                CategoryFactory.createCategory(this.categoryRepository.nextIdentity(), "Business Casual MZ", "Casual accommodation for 3 persons.", 3, p, p)
        );
        categoriesExpected.forEach(category -> {
            this.categoryRepository.store(category);
        });
        this.em.flush();

        // when
        List<Category> categoriesActual = this.categoryRepository.findAll();

        // then
        assertEquals(categoriesExpected.size(), categoriesActual.size());
        for (Category c : categoriesActual) {
            assertTrue(categoriesExpected.contains(c));
        }
    }

    @Test
    void given_emptyrepository_when_findallcategories_then_returnemptycategories() {
        assertTrue(this.categoryRepository.findAll().isEmpty());
    }

    @Test
    void given_category_when_addcategorytorepository_then_returnequalscategory() {
        // given
        Price p = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Category categoryExcepted = CategoryFactory.createCategory(
                this.categoryRepository.nextIdentity(),
                "Family Suite MZ",
                "A family suite ...",
                3,
                p,
                p
        );

        // when
        this.categoryRepository.store(categoryExcepted);
        this.em.flush();
        Category categoryActual = this.categoryRepository.findByName(categoryExcepted.getName()).get();

        // then
        assertEquals(categoryExcepted, categoryActual);
        assertEquals(categoryExcepted.getCategoryId(), categoryActual.getCategoryId());
    }

    @Test
    void given_3categoryroomsandroomstate_when_findcategoryroomsbyroomstate_then_returnequalscategoryrooms() throws RoomAlreadyExistsException {
        // given
        RoomState roomState = RoomState.AVAILABLE;
        List<Room> roomsExpected = Arrays.asList(
                new Room(new RoomNumber("101"), roomState),
                new Room(new RoomNumber("102"), roomState),
                new Room(new RoomNumber("103"), roomState)
        );
        Price p = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(
                this.categoryRepository.nextIdentity(),
                "Family Suite MZ",
                "A family suite ...",
                3,
                p,
                p
        );
        for (Room r : roomsExpected) {
            category.createRoom(r);
        }
        this.categoryRepository.store(category);
        this.em.flush();

        // when
        List<Room> roomsActual = this.categoryRepository.findCategoryRoomsByState(category.getName(), roomState);

        // then
        assertEquals(roomsExpected.size(), roomsActual.size());
        for (Room r : roomsActual) {
            assertTrue(roomsExpected.contains(r));
        }
    }

    @Test
    void given_3categoryroomsanddifferentroomstate_when_findcategoryroomsbyroomstate_then_returnemptycategoryrooms() throws RoomAlreadyExistsException {
        // given
        RoomState roomState = RoomState.AVAILABLE;
        RoomState differentRoomState = RoomState.MAINTENANCE;
        List<Room> roomsExpected = Arrays.asList(
                new Room(new RoomNumber("101"), roomState),
                new Room(new RoomNumber("102"), roomState),
                new Room(new RoomNumber("103"), roomState)
        );
        Price p = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(
                this.categoryRepository.nextIdentity(),
                "Family Suite MZ",
                "A family suite ...",
                3,
                p,
                p
        );
        for (Room r : roomsExpected) {
            category.createRoom(r);
        }
        this.categoryRepository.store(category);
        this.em.flush();

        // when
        List<Room> roomsActual = this.categoryRepository.findCategoryRoomsByState(category.getName(), differentRoomState);

        // then
        assertTrue(roomsActual.isEmpty());
    }

    @Test
    void given_3categoryrooms_when_findroomsbystayid_then_returnequalscategoryrooms() throws RoomAlreadyExistsException {
        //given
        RoomState roomState = RoomState.AVAILABLE;
        List<Room> roomsExpected = Arrays.asList(
                new Room(new RoomNumber("101"), roomState),
                new Room(new RoomNumber("102"), roomState),
                new Room(new RoomNumber("103"), roomState)
        );

        Price p = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(
                this.categoryRepository.nextIdentity(),
                "Honeymoon Suite DZ",
                "A honeymoon..",
                2,
                p,
                p
        );

        for (Room r : roomsExpected) {
            r.occupied(getContextLocalDate(), getContextLocalDate().plusDays(2), new StayId("1"));
            category.createRoom(r);
        }

        this.categoryRepository.store(category);
        this.em.flush();

        //when
        List<Room> roomsActual = this.categoryRepository.findRoomsByStayId(new StayId("1"));

        //then
        for (Room r : roomsActual) {
            assertTrue(roomsExpected.contains(r));
        }
    }
}

