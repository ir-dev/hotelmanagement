package at.fhv.hotelmanagement.domain.infrastructure;

import at.fhv.hotelmanagement.domain.model.Booking;
import at.fhv.hotelmanagement.domain.model.BookingNo;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Component
public class HibernateBookingRepository implements BookingRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Booking> findAll() {
        TypedQuery<Booking> query = this.em.createQuery("SELECT b FROM Booking b", Booking.class);
        return query.getResultList();
    }

    @Override
    public Optional<Booking> findByNo(BookingNo bookingNo) {
        TypedQuery<Booking> query = this.em.createQuery("FROM Booking AS b WHERE b.bookingNo = :bookingNo", Booking.class);
        query.setParameter("bookingId", bookingNo);
//        return singleResultOptional(query);
        return Optional.empty();
    }

    @Override
    public BookingNo nextIdentity() {
        return new BookingNo(java.util.UUID.randomUUID().toString().toUpperCase());
    }

    @Override
    public void store(Booking booking) {
        this.em.persist(booking);
        this.em.flush();
    }
}
