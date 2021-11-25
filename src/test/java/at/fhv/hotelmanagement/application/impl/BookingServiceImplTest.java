package at.fhv.hotelmanagement.application.impl;
import at.fhv.hotelmanagement.application.api.BookingsService;
import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.BookingDetailsDTO;
import at.fhv.hotelmanagement.application.dto.GuestDTO;
import at.fhv.hotelmanagement.domain.infrastructure.HibernateBookingRepository;
import at.fhv.hotelmanagement.domain.infrastructure.HibernateGuestRepository;
import at.fhv.hotelmanagement.domain.model.*;
import at.fhv.hotelmanagement.domain.model.enums.Country;
import at.fhv.hotelmanagement.domain.model.enums.PaymentType;
import at.fhv.hotelmanagement.domain.model.enums.Salutation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookingServiceImplTest {

    @Autowired
    private BookingsService bookingsService;

    @MockBean
    private HibernateBookingRepository bookingRepository;

    @MockBean
    private HibernateGuestRepository guestRepository;

    @Test
    void given_emptyrepository_when_fetchingallbookings_then_empty() {
        //given
        Mockito.when(bookingRepository.findAll()).thenReturn(Collections.emptyList());

        //when
        List<BookingDTO> bookingsDto = bookingsService.allBookings();

        //then
        assertTrue(bookingsDto.isEmpty());
    }

    @Test
    void given_2bookingsinrepository_when_fetchallbookings_then_returnequalbookings() {
        //given
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = LocalDate.now().plusDays(2);
        LocalTime arrivalTime = LocalTime.of(11, 30);
        Integer numberOfPersons = 4;
        Map<String, Integer> selectCategoriesRoomCount = new HashMap<>();
        selectCategoriesRoomCount.put("Honeymoon Suite DT", 2);
        GuestId guestId = new GuestId("1");
        Address ad1 = new Address("Musterstrasse 1", "6850", "Dornbirn", String.valueOf(Country.AT));
        Guest g = new Guest(guestId, null, String.valueOf(Salutation.MISTER), "Fritz", "Mayer", LocalDate.of(1979, 12, 24), ad1, "");
        PaymentInformation paymentInformation = new PaymentInformation("Hans-Peter Mayer", "5432 9876 5678 1234", "12/21", "123", String.valueOf(PaymentType.INVOICE));

        Mockito.when(this.bookingRepository.nextIdentity()).thenReturn(new BookingNo(java.util.UUID.randomUUID().toString().toUpperCase()));

        BookingNo bookingNo1 = this.bookingRepository.nextIdentity();
        BookingNo bookingNo2 = this.bookingRepository.nextIdentity();
        List<BookingNo> bookingNos = Arrays.asList(bookingNo1, bookingNo2);
        List<Booking> bookings = bookingNos.stream()
                .map(bookingNo -> new Booking(bookingNo, arrivalDate, departureDate, arrivalTime, numberOfPersons, selectCategoriesRoomCount, guestId, paymentInformation))
                .collect(Collectors.toList());

        Mockito.when(this.bookingRepository.findAll()).thenReturn(bookings);
        Mockito.when(this.guestRepository.findById(guestId)).thenReturn(Optional.of(g));

        List<BookingDTO> bookingsDtoExpected = new ArrayList<>();
        for (Booking b : bookings) {
            GuestDTO guestDto = GuestDTO.builder().withGuestEntity(g).build();
            BookingDTO bookingDto = BookingDTO.builder().withBookingEntity(b).withDetails(BookingDetailsDTO.builder().withBookingEntity(b).withGuestDTO(guestDto).build()).build();
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
    void given_bookinginrepository_when_bybookingNo_then_return() {
        //given
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = LocalDate.now().plusDays(2);
        LocalTime arrivalTime = LocalTime.of(11, 30);
        Integer numberOfPersons = 4;
        Map<String, Integer> selectCategoriesRoomCount = new HashMap<>();
        selectCategoriesRoomCount.put("Honeymoon Suite DT", 2);
        GuestId guestId = new GuestId("1");
        Address ad1 = new Address("Musterstrasse 1", "6850", "Dornbirn", String.valueOf(Country.AT));
        Guest guest = new Guest(guestId, null, String.valueOf(Salutation.MISTER), "Fritz", "Mayer", LocalDate.of(1979, 12, 24), ad1, "");
        PaymentInformation paymentInformation = new PaymentInformation("Hans-Peter Mayer", "5432 9876 5678 1234", "12/21", "123", String.valueOf(PaymentType.INVOICE));

        Mockito.when(this.bookingRepository.nextIdentity()).thenReturn(new BookingNo(java.util.UUID.randomUUID().toString().toUpperCase()));

        BookingNo bookingNo = this.bookingRepository.nextIdentity();
        Booking booking = new Booking(bookingNo, arrivalDate, departureDate, arrivalTime, numberOfPersons, selectCategoriesRoomCount, guestId, paymentInformation);

        Mockito.when(this.guestRepository.findById(guestId)).thenReturn(Optional.of(guest));

        BookingDTO expectedBookingDTO = BookingDTO.builder()
                .withBookingEntity(booking)
                .withDetails(BookingDetailsDTO.builder()
                        .withBookingEntity(booking)
                        .withGuestDTO(GuestDTO.builder().withGuestEntity(guest).build())
                        .build())
                .build();


        Mockito.when(this.bookingRepository.findByNo(bookingNo)).thenReturn(Optional.of(booking));

        //when
        BookingDTO actualBookingDTO = this.bookingsService.bookingByBookingNo(bookingNo.getNo()).get();

        //then
        assertEquals(expectedBookingDTO, actualBookingDTO);
    }


    @Test
    void given_bookingdetailsinrepository_when_bybookingNo_then_return() {
        //given
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = LocalDate.now().plusDays(2);
        LocalTime arrivalTime = LocalTime.of(11, 30);
        Integer numberOfPersons = 4;
        Map<String, Integer> selectCategoriesRoomCount = new HashMap<>();
        selectCategoriesRoomCount.put("Honeymoon Suite DT", 2);
        GuestId guestId = new GuestId("1");
        Address ad1 = new Address("Musterstrasse 1", "6850", "Dornbirn", String.valueOf(Country.AT));
        Guest guest = new Guest(guestId, null, String.valueOf(Salutation.MISTER), "Fritz", "Mayer", LocalDate.of(1979, 12, 24), ad1, "");
        PaymentInformation paymentInformation = new PaymentInformation("Hans-Peter Mayer", "5432 9876 5678 1234", "12/21", "123", String.valueOf(PaymentType.INVOICE));

        Mockito.when(this.bookingRepository.nextIdentity()).thenReturn(new BookingNo(java.util.UUID.randomUUID().toString().toUpperCase()));

        BookingNo bookingNo = this.bookingRepository.nextIdentity();
        Booking booking = new Booking(bookingNo, arrivalDate, departureDate, arrivalTime, numberOfPersons, selectCategoriesRoomCount, guestId, paymentInformation);

        Mockito.when(this.guestRepository.findById(guestId)).thenReturn(Optional.of(guest));

        BookingDetailsDTO expectedBookingDetailsDTO = BookingDetailsDTO.builder()
                        .withBookingEntity(booking)
                        .withGuestDTO(GuestDTO.builder().withGuestEntity(guest).build())
                        .build();


        Mockito.when(this.bookingRepository.findByNo(bookingNo)).thenReturn(Optional.of(booking));

        //when
        BookingDetailsDTO actualBookingDetailsDTO = this.bookingsService.bookingDetailsByBookingNo(bookingNo.getNo()).get();

        //then
        assertEquals(expectedBookingDetailsDTO, actualBookingDetailsDTO);
    }
}