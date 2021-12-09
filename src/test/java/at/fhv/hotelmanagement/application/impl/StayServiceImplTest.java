package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.application.api.StayService;
import at.fhv.hotelmanagement.application.dto.StayDTO;
import at.fhv.hotelmanagement.infrastructure.HibernateStayRepository;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.booking.Booking;
import at.fhv.hotelmanagement.domain.model.booking.BookingFactory;
import at.fhv.hotelmanagement.domain.model.booking.BookingNo;
import at.fhv.hotelmanagement.domain.model.booking.CreateBookingException;
import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.category.CategoryFactory;
import at.fhv.hotelmanagement.domain.model.category.CategoryId;
import at.fhv.hotelmanagement.domain.model.category.RoomAlreadyExistsException;
import at.fhv.hotelmanagement.domain.model.category.Room;
import at.fhv.hotelmanagement.domain.model.category.RoomNumber;
import at.fhv.hotelmanagement.domain.model.category.RoomState;
import at.fhv.hotelmanagement.domain.model.guest.CreateGuestException;
import at.fhv.hotelmanagement.domain.model.guest.GuestId;
import at.fhv.hotelmanagement.domain.model.guest.PaymentInformation;
import at.fhv.hotelmanagement.domain.model.guest.PaymentType;
import at.fhv.hotelmanagement.domain.model.stay.CreateStayException;
import at.fhv.hotelmanagement.domain.model.stay.Stay;
import at.fhv.hotelmanagement.domain.model.stay.StayFactory;
import at.fhv.hotelmanagement.domain.model.stay.StayId;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class StayServiceImplTest extends AbstractTest {

    @Autowired
    private StayService stayService;

    @MockBean
    private HibernateStayRepository stayRepository;

    @MockBean
    private BookingRepository bookingRepository;

    private static Integer nextDummyBookingIdentity = 1;
    private static Integer nextDummyStayIdentity = 1;

    @Test
    void given_emptyrepository_when_fetchingallstays_then_empty() {
        //given
        Mockito.when(this.stayRepository.findAll()).thenReturn(Collections.emptyList());
        //when
        List<StayDTO> staysDTO = this.stayService.allStays();
        //then
        assertTrue(staysDTO.isEmpty());
    }

    @Test
    void given_2staysinrepository_when_fetchallstays_then_returnequalStays() throws RoomAlreadyExistsException, CreateBookingException, CreateStayException {
        //given
        List<Stay> stays = Arrays.asList(createStayDummy(), createStayDummy());

        Mockito.when(this.stayRepository.findAll()).thenReturn(stays);

        List<StayDTO> staysDtoExpected = new ArrayList<>();
        for (Stay s : stays) {
            StayDTO stayDTO = StayDTO.builder()
                    .withStayEntity(s)
                    .build();
            staysDtoExpected.add(stayDTO);
        }

        //when
        List<StayDTO> staysDtoActual = this.stayService.allStays();

        //then
        for (StayDTO s : staysDtoActual) {
            assertTrue(staysDtoExpected.contains(s));
        }
    }

    @Test
    void given_stayinrepository_when_bystayId_then_return() throws RoomAlreadyExistsException, CreateBookingException, CreateStayException, CreateGuestException {
        //given
        Stay stay = createStayDummy();
        StayDTO expectedStayDTO = StayDTO.builder()
                .withStayEntity(stay)
                .build();

        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));

        //when
        StayDTO actualStayDTO = this.stayService.stayByStayId(stay.getStayId().getId()).get();

        //then
        assertEquals(expectedStayDTO, actualStayDTO);
    }


    private Stay createStayDummy() throws CreateBookingException, RoomAlreadyExistsException, CreateStayException {
        //Category
        Price p = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(new CategoryId("1"),"Business Casual EZ", "A casual accommodation for business guests.", 1, p, p);
        category.createRoom(new Room(new RoomNumber("100"), RoomState.AVAILABLE));

        //Booking
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = LocalDate.now().plusDays(2);
        LocalTime arrivalTime = LocalTime.now();
        Integer numberOfPersons = 1;
        Map<Category, Integer> selectCategoriesRoomCount = new HashMap<>();
        selectCategoriesRoomCount.put(category, 1);
        GuestId guestId = new GuestId("1");
        PaymentInformation paymentInformation = new PaymentInformation("Franz Huber", "1234 5678 9012 3456", "05/24", "123", String.valueOf(PaymentType.CASH));

        BookingNo bookingNo = this.bookingRepository.nextIdentity();
        Booking booking = BookingFactory.createBooking(
                nextDummyBookingIdentity(),
                arrivalDate,
                departureDate,
                arrivalTime,
                numberOfPersons,
                selectCategoriesRoomCount,
                guestId,
                paymentInformation);

        return StayFactory.createStayForBooking(
                nextDummyStayIdentity(),
                booking,
                bookingNo,
                arrivalDate,
                departureDate,
                numberOfPersons,
                selectCategoriesRoomCount,
                guestId,
                paymentInformation
        );
    }

    private StayId nextDummyStayIdentity() {
        return new StayId((nextDummyStayIdentity++).toString());
    }

    private BookingNo nextDummyBookingIdentity() {
        return new BookingNo((nextDummyBookingIdentity++).toString());
    }

}