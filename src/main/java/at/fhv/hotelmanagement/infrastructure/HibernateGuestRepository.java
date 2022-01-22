package at.fhv.hotelmanagement.infrastructure;

import at.fhv.hotelmanagement.domain.model.guest.Guest;
import at.fhv.hotelmanagement.domain.model.guest.GuestId;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class HibernateGuestRepository implements GuestRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public GuestId nextIdentity() {
        return new GuestId(java.util.UUID.randomUUID().toString().toUpperCase());
    }

    @Override
    public List<Guest> findAll() {
        TypedQuery<Guest> query = this.em.createQuery("SELECT g FROM Guest g", Guest.class);
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
