package at.fhv.hotelmanagement.bdd.steps;

import at.fhv.hotelmanagement.application.api.StayService;
import at.fhv.hotelmanagement.application.converters.CategoryConverter;
import at.fhv.hotelmanagement.application.impl.EntityNotFoundException;
import at.fhv.hotelmanagement.bdd.runner.ScenarioTXBoundary;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.PriceCurrencyMismatchException;
import at.fhv.hotelmanagement.domain.model.category.*;
import at.fhv.hotelmanagement.domain.model.guest.*;
import at.fhv.hotelmanagement.domain.model.stay.*;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import at.fhv.hotelmanagement.domain.repositories.StayRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@CucumberContextConfiguration
@SpringBootTest
@Transactional
public class StaySteps extends ScenarioTXBoundary {

    @Autowired
    private StayService stayService;

    @Autowired
    private StayRepository stayRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Before
    private void beforeScenario(){
        this.beginTX();
    }

    @After
    private void afterScenario(){
        this.rollbackTX();
    }

    @Given("a category with name {string} and max persons {int} and half board price {int} full board price {int} and currency {word}")
    public void setupFirstSelectedCategory(String name, int maxPersons, int halfboard, int fullboard, String currency) throws RoomAlreadyExistsException {
        Currency curr = Currency.getInstance(currency);
        Category category = CategoryFactory.createCategory(this.categoryRepository.nextIdentity(), name, "", maxPersons, Price.of(BigDecimal.valueOf(halfboard), curr), Price.of(BigDecimal.valueOf(fullboard), curr));
        this.categoryRepository.store(category);

        category.createRoom(new Room(new RoomNumber("120"), RoomState.AVAILABLE));
        category.createRoom(new Room(new RoomNumber("121"), RoomState.AVAILABLE));
    }

    @Given("a second category with name {string} and max persons {int} and half board price {int} full board price {int} and currency {word}")
    public void setupSecondSelectedCategory(String name, int maxPersons, int halfboard, int fullboard, String currency) throws RoomAlreadyExistsException {
        Currency curr = Currency.getInstance(currency);
        Category category = CategoryFactory.createCategory(this.categoryRepository.nextIdentity(),name, "", maxPersons, Price.of(BigDecimal.valueOf(halfboard), curr), Price.of(BigDecimal.valueOf(fullboard), curr));
        this.categoryRepository.store(category);

        category.createRoom(new Room(new RoomNumber("220"), RoomState.AVAILABLE));
    }


    @Given("a stay with stayId {word} and arrivalDate today and departureDate {word} and number of persons {int} " +
            "and payment information with cardHolderName {word} and cardNumber {string} and cardValidThru {word} " +
            "and cardCvc {word} and paymentType {word} and selected categories room count")
    public void setupStayCheckingOut(String stayId, String departureDate, Integer numberOfPersons, String cardHolderName, String cardNumber, String cardValidThru, String cardCvc, String paymentType,
                                     DataTable selectedCategoriesRoomCountTable) throws CreateStayException, EntityNotFoundException {
        PaymentInformation paymentInformation = new PaymentInformation(cardHolderName, cardNumber, cardValidThru, cardCvc, paymentType);
        System.out.println(paymentInformation.getCardHolderName());

        Map<String, Integer> selectedCategoriesRoomCountMap = selectedCategoriesRoomCountTable.asMap(String.class, Integer.class);

        setupStayForWalkinGuest(
                stayId,
                LocalDate.now(),
                LocalDate.parse(departureDate),
                numberOfPersons,
                selectedCategoriesRoomCountMap,
                new GuestId("G100090"),
                paymentInformation
        );
    }


    @Given("a guest with guestId {word} and salutation {word} and first name {word} and last name {word} and date of birth {word} and street {word} and zipcode {word} and city {word} and country {word}")
    public void setupGuest(String guestId, String salutation, String firstName, String lastName, String dateOfBirth, String street, String zipcode, String city, String country) throws CreateGuestException {
        Address address = new Address(street, zipcode, city, Country.valueOf(country).toString());
        Guest guest = GuestFactory.createGuest(new GuestId(guestId), null, Salutation.valueOf(salutation).toString(), firstName, lastName, LocalDate.parse(dateOfBirth), address, "");
        this.guestRepository.store(guest);
    }


    private void setupStayForWalkinGuest(String stayId, LocalDate arrivalDate, LocalDate departureDate,
                                         Integer numberOfPersons, Map<String, Integer> selectedCategoryNamesRoomCount,
                                         GuestId guestId, PaymentInformation paymentInformation) throws EntityNotFoundException, CreateStayException {
        Map<Category, Integer> selectedCategoriesRoomCount = CategoryConverter.convertToSelectedCategoriesRoomCount(selectedCategoryNamesRoomCount);

        Stay stay = StayFactory.createStayForWalkIn(
                new StayId(stayId),
                arrivalDate,
                departureDate,
                numberOfPersons,
                selectedCategoriesRoomCount,
                guestId,
                paymentInformation
        );

        this.stayRepository.store(stay);
    }

    @When("Stay with stayId {word} is charged with all selected line items")
    public void chargeStay(String stayId, Map<String, Integer> selectedLineItemProductNamesCount) {
        try {
            this.stayService.chargeStay(stayId, selectedLineItemProductNamesCount);
        } catch (EntityNotFoundException | PriceCurrencyMismatchException e) {
            e.printStackTrace();
        }
    }

    @When("Stay with stayId {word} is checked out")
    public void checkout(String stayId) {
        try {
            this.stayService.checkoutStay(stayId);
        } catch (EntityNotFoundException | BillingOpenException e) {
            e.printStackTrace();
        }
    }

    @Then("Stay with stayId {word} should be {word}")
    public void expectCheckedOutStay(String stayId, String stayState) {
        Stay stay = this.stayRepository.findById(new StayId(stayId)).orElseThrow();

        assertEquals(StayState.valueOf(stayState), stay.getStayState());
    }
}
