package at.fhv.hotelmanagement.domain.infrastructure;

import at.fhv.hotelmanagement.domain.model.Stay;
import at.fhv.hotelmanagement.domain.model.StayId;
import at.fhv.hotelmanagement.domain.repositories.StayRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Component
public class HibernateStayRepository implements StayRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public StayId nextIdentity() {
        return new StayId(java.util.UUID.randomUUID().toString().toUpperCase());
    }

    @Override
    public List<Stay> findAll() {
        TypedQuery<Stay> query = this.em.createQuery("SELECT s FROM Stay s", Stay.class);
        return query.getResultList();
    }

    @Override
    public Optional<Stay> findById(StayId stayId) {
        TypedQuery<Stay> query = this.em.createQuery("FROM Stay AS s WHERE s.stayId = :stayId", Stay.class);
        query.setParameter("stayId", stayId);
        return query.getResultStream().findFirst();
    }

    @Override
    public void store(Stay stay) {
        this.em.persist(stay);
    }
}
