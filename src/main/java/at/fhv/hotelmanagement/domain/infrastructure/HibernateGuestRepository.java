package at.fhv.hotelmanagement.domain.infrastructure;

import at.fhv.hotelmanagement.domain.model.Booking;
import at.fhv.hotelmanagement.domain.model.Guest;
import at.fhv.hotelmanagement.domain.model.GuestId;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Component
public class HibernateGuestRepository implements GuestRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Guest> findAll() {
        TypedQuery<Guest> query = this.em.createQuery("SELECT b FROM Guest b", Guest.class);
        return query.getResultList();
    }

    @Override
    public Optional<Guest> findById(GuestId guestId) {
        TypedQuery<Guest> query = this.em.createQuery("FROM Guest AS g WHERE g.guestId = :guestId", Guest.class);
        query.setParameter("guestId", guestId);
        return query.getResultStream().findFirst();
    }

    @Override
    public void store(Guest guest) {
        this.em.persist(guest);
    }
}
