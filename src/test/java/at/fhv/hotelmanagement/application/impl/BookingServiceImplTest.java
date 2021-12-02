package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.BookingsService;
import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.BookingDetailsDTO;
import at.fhv.hotelmanagement.application.dto.GuestDTO;
import at.fhv.hotelmanagement.domain.infrastructure.HibernateBookingRepository;
import at.fhv.hotelmanagement.domain.infrastructure.HibernateGuestRepository;
import at.fhv.hotelmanagement.domain.model.booking.Booking;
import at.fhv.hotelmanagement.domain.model.booking.BookingFactory;
import at.fhv.hotelmanagement.domain.model.booking.BookingNo;
import at.fhv.hotelmanagement.domain.model.booking.CreateBookingException;
import at.fhv.hotelmanagement.domain.model.category.*;
import at.fhv.hotelmanagement.domain.model.category.room.AlreadyExistsException;
import at.fhv.hotelmanagement.domain.model.category.room.Room;
import at.fhv.hotelmanagement.domain.model.category.room.RoomNumber;
import at.fhv.hotelmanagement.domain.model.guest.Country;
import at.fhv.hotelmanagement.domain.model.guest.PaymentType;
import at.fhv.hotelmanagement.domain.model.category.room.RoomState;
import at.fhv.hotelmanagement.domain.model.guest.Salutation;
import at.fhv.hotelmanagement.domain.model.guest.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class BookingServiceImplTest {

    @Autowired
    private BookingsService bookingsService;

    @MockBean
    private HibernateBookingRepository bookingRepository;

    @MockBean
    private HibernateGuestRepository guestRepository;


    private static Integer nextDummyBookingIdentity = 1;

    @Test
    void given_emptyrepository_when_fetchingallbookings_then_empty() {
        //given
        Mockito.when(this.bookingRepository.findAll()).thenReturn(Collections.emptyList());
        //when
        List<BookingDTO> bookingsDto = this.bookingsService.allBookings();
        //then
        assertTrue(bookingsDto.isEmpty());
    }

    @Test
    void given_2bookingsinrepository_when_fetchallbookings_then_returnequalbookings() throws CreateGuestException, AlreadyExistsException, CreateBookingException {
        //given
        List<Booking> bookings = Arrays.asList(
                createBookingDummy(),
                createBookingDummy()
        );

        Mockito.when(this.bookingRepository.findAll()).thenReturn(bookings);
        Mockito.when(this.guestRepository.findById(new GuestId("1"))).thenReturn(Optional.of(createGuestDummy()));

        List<BookingDTO> bookingsDtoExpected = new ArrayList<>();
        for (Booking booking : bookings) {
            GuestDTO guestDto = GuestDTO.builder().withGuestEntity(createGuestDummy()).build();
            BookingDTO bookingDto = BookingDTO.builder().withBookingEntity(booking).withDetails(BookingDetailsDTO.builder().withBookingEntity(booking).withGuestDTO(guestDto).build()).build();
            bookingsDtoExpected.add(bookingDto);
        }

        //when
        List<BookingDTO> bookingsDtoActual = this.bookingsService.allBookings();

        //then
        for (BookingDTO b : bookingsDtoActual) {
            assertTrue(bookingsDtoExpected.contains(b));
        }
    }


    @Test
    void given_bookinginrepository_when_bybookingNo_then_return() throws CreateGuestException, CreateBookingException, AlreadyExistsException {
        //given
        Booking booking = createBookingDummy();

        Mockito.when(this.guestRepository.findById(new GuestId("1"))).thenReturn(Optional.of(createGuestDummy()));

        BookingDTO expectedBookingDTO = BookingDTO.builder()
                .withBookingEntity(booking)
                .withDetails(BookingDetailsDTO.builder()
                        .withBookingEntity(booking)
                        .withGuestDTO(GuestDTO.builder().withGuestEntity(createGuestDummy()).build())
                        .build())
                .build();

        Mockito.when(this.bookingRepository.findByNo(booking.getBookingNo())).thenReturn(Optional.of(booking));

        //when
        BookingDTO actualBookingDTO = this.bookingsService.bookingByBookingNo(booking.getBookingNo().getNo()).get();

        //then
        assertEquals(expectedBookingDTO, actualBookingDTO);
    }


    @Test
    void given_bookingdetailsinrepository_when_bybookingNo_then_return() throws CreateGuestException, CreateBookingException, AlreadyExistsException {
        //given
        Booking booking = createBookingDummy();

        Mockito.when(this.guestRepository.findById(new GuestId("1"))).thenReturn(Optional.of(createGuestDummy()));

        BookingDetailsDTO expectedBookingDetailsDTO = BookingDetailsDTO.builder()
                .withBookingEntity(booking)
                .withGuestDTO(GuestDTO.builder().withGuestEntity(createGuestDummy()).build())
                .build();


        Mockito.when(this.bookingRepository.findByNo(booking.getBookingNo())).thenReturn(Optional.of(booking));

        //when
        BookingDetailsDTO actualBookingDetailsDTO = this.bookingsService.bookingDetailsByBookingNo(booking.getBookingNo().getNo()).get();

        //then
        assertEquals(expectedBookingDetailsDTO, actualBookingDetailsDTO);
    }


    private Guest createGuestDummy() throws CreateGuestException {
        Address address = new Address("Musterstrasse 1", "6850", "Dornbirn", String.valueOf(Country.AT));
        return GuestFactory.createGuest(
                new GuestId("1"),
                null, String.valueOf(Salutation.MISTER),
                "Fritz",
                "Mayer",
                LocalDate.of(1979, 12, 24),
                address,
                ""
        );
    }


    private Booking createBookingDummy() throws CreateBookingException, AlreadyExistsException {
        Category category = CategoryFactory.createCategory(new CategoryId("1"),"Business Casual EZ", "A casual accommodation for business guests.", 1);
        category.createRoom(new Room(new RoomNumber("100"), RoomState.AVAILABLE));

        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = LocalDate.now().plusDays(2);
        LocalTime arrivalTime = LocalTime.of(11, 30);
        Integer numberOfPersons = 1;
        Map<Category, Integer> selectCategoriesRoomCount = new HashMap<>();
        selectCategoriesRoomCount.put(category, 1);
        GuestId guestId = new GuestId("1");
        PaymentInformation paymentInformation = new PaymentInformation("Hans-Peter Mayer", "5432 9876 5678 1234", "12/21", "123", String.valueOf(PaymentType.INVOICE));

        return BookingFactory.createBooking(
                nextDummyBookingIdentity(),
                arrivalDate,
                departureDate,
                arrivalTime,
                numberOfPersons,
                selectCategoriesRoomCount,
                guestId,
                paymentInformation
        );
    }

    private BookingNo nextDummyBookingIdentity() {
        return new BookingNo((nextDummyBookingIdentity++).toString());
    }
}