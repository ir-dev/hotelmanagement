package at.fhv.hotelmanagement.infrastructure;

import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.category.CategoryId;
import at.fhv.hotelmanagement.domain.model.category.Room;
import at.fhv.hotelmanagement.domain.model.category.RoomState;
import at.fhv.hotelmanagement.domain.model.stay.StayId;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Component
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
    public List<Room> findCategoryRoomsByState(String categoryName, RoomState state) {
        TypedQuery<Room> query = this.em.createQuery("SELECT cr FROM Category c JOIN c.rooms cr WHERE c.name = :categoryName AND cr.roomState = :state", Room.class);
        query.setParameter("categoryName", categoryName);
        query.setParameter("state", state);
        return query.getResultList();
    }

    @Override
    public List<Room> findRoomsByStayId(StayId stayId) {
        TypedQuery<Room> query = this.em.createQuery("SELECT r FROM Room r JOIN r.roomOccupancies ro WHERE ro.stayId = :stayId", Room.class);
        query.setParameter("stayId", stayId);
        return query.getResultList();
    }

    @Override
    public void store(Category category) {
        this.em.persist(category);
    }
}
