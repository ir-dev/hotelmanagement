package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.BookingsService;
import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.BookingDetailsDTO;
import at.fhv.hotelmanagement.domain.model.Booking;
import at.fhv.hotelmanagement.domain.model.enums.BookingStatus;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import at.fhv.hotelmanagement.view.forms.BookingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookingServiceImpl implements BookingsService {

    @Autowired
    BookingRepository bookingRepository;

    @Override
    public List<BookingDTO> getAll() {
        List<Booking> bookings = bookingRepository.getAll();

        List<BookingDTO> bookingsDto = new ArrayList<>();
        for(Booking b: bookings){
            bookingsDto.add(BookingDTO.builder().withBookingEntity(b).build());
        }
        return bookingsDto;
    }

    @Override
    public Optional<BookingDTO> getByBookingNr(String bookingNr) {
        Optional<Booking> b = bookingRepository.findByBookingNr(bookingNr);
        if(b.isEmpty()){
            return Optional.empty();
        }
        Booking booking = b.get();

        return Optional.of(BookingDTO.builder()
                .withBookingEntity(booking)
                .build());
    }

    @Override
    public Optional<BookingDetailsDTO> getDetailsByBookingNr(String bookingNr) {
        Optional<BookingDTO> b = getByBookingNr(bookingNr);
        Optional<Booking> b2 = bookingRepository.findByBookingNr(bookingNr);

        if(b.isEmpty() || b2.isEmpty()){
            return Optional.empty();
        }
        Booking booking = b2.get();
        return Optional.of(BookingDetailsDTO.builder()
                .withBookingDTO(b.get())
                .withBookingEntity(booking)
                .build());
    }

    @Override
    public void store(BookingForm b) {
        LocalDate arrivalDate = LocalDate.parse(b.getArrivalDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate departureDate = LocalDate.parse(b.getDepartureDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalTime arrivalTime = LocalTime.parse(b.getArrivalTime(), DateTimeFormatter.ofPattern("H:mm"));


        Booking booking = new Booking("1234", "1",arrivalDate, departureDate, BookingStatus.Pending, 2, arrivalTime, b.getAddress());

        bookingRepository.save(booking);
    }


    public void setArrivalDate(String arrivalDate) {
        //        this.arrivalDate = LocalDate.parse(arrivalDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //    }
    }
}
