package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.BookingsService;
import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.BookingDetailsDTO;
import at.fhv.hotelmanagement.domain.model.Booking;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

        System.out.println("nr:" + bookings.get(0).bookingNr());

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

        return Optional.ofNullable(BookingDTO.builder()
                .withBookingEntity(booking)
                .build());
    }

    @Override
    public Optional<BookingDetailsDTO> getDetailsByBookingNr(String bookingNr) {
        Optional<BookingDTO> b = getByBookingNr(bookingNr);

        Optional<Booking> b2 = bookingRepository.findByBookingNr(bookingNr);
        if(b.isEmpty()){
            return Optional.empty();
        }
        Booking booking = b2.get();

        return Optional.ofNullable(BookingDetailsDTO.builder()
                .withBookingDTO(b.get())
                .withBookingEntity(booking)
                .build());
    }
}
