package at.fhv.hotelmanagement.infrastructure;

import at.fhv.hotelmanagement.domain.model.booking.Booking;
import at.fhv.hotelmanagement.domain.model.booking.BookingNo;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Component
public class HibernateBookingRepository implements BookingRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public BookingNo nextIdentity() {
        Query query = this.em.createNativeQuery("select next value for seq_bookingno");
        String key = query.getSingleResult().toString();
        return new BookingNo("B" + key);
    }

    @Override
    public List<Booking> findAll() {
        TypedQuery<Booking> query = this.em.createQuery("SELECT b FROM Booking b", Booking.class);
        return query.getResultList();
    }

    @Override
    public Optional<Booking> findByNo(BookingNo bookingNo) {
        TypedQuery<Booking> query = this.em.createQuery("FROM Booking AS b WHERE b.bookingNo = :bookingNo", Booking.class);
        query.setParameter("bookingNo", bookingNo);
        return query.getResultStream().findFirst();
    }

    @Override
    public void store(Booking booking) {
        this.em.persist(booking);
    }
}
