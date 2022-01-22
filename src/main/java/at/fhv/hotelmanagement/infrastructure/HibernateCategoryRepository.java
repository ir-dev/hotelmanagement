package at.fhv.hotelmanagement.infrastructure;

import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.category.CategoryId;
import at.fhv.hotelmanagement.domain.model.category.RoomNumber;
import at.fhv.hotelmanagement.domain.model.stay.StayId;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class HibernateCategoryRepository implements CategoryRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public CategoryId nextIdentity() {
        return new CategoryId(java.util.UUID.randomUUID().toString().toUpperCase());
    }

    @Override
    public List<Category> findAll() {
        TypedQuery<Category> query = this.em.createQuery("FROM Category c", Category.class);
        return query.getResultList();
    }

    @Override
    public Optional<Category> findByName(String categoryName) {
        TypedQuery<Category> query = this.em.createQuery("FROM Category AS c WHERE c.name = :categoryName", Category.class);
        query.setParameter("categoryName", categoryName);
        return query.getResultStream().findFirst();
    }

    @Override
    public List<RoomNumber> findRoomNumbersByStayId(StayId stayId) {
        TypedQuery<RoomNumber> query = this.em.createQuery("SELECT r.roomNumber FROM Room r JOIN r.roomOccupancies ro WHERE ro.stayId = :stayId", RoomNumber.class);
        query.setParameter("stayId", stayId);
        return query.getResultList();
    }

    @Override
    public void store(Category category) {
        this.em.persist(category);
    }
}
