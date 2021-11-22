package at.fhv.hotelmanagement.domain.infrastructure;

import at.fhv.hotelmanagement.domain.model.*;
import at.fhv.hotelmanagement.domain.model.enums.RoomState;
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
    public List<Category> findAll() {
        TypedQuery<Category> query = this.em.createQuery("SELECT c FROM Category c", Category.class);
        return query.getResultList();
    }

    @Override
    public Optional<Category> findByName(String categoryName) {
        TypedQuery<Category> query = this.em.createQuery("FROM Category AS c WHERE c.name = :categoryName", Category.class);
        query.setParameter("categoryName", categoryName);
        return query.getResultStream().findFirst();
    }

    @Override
    public List<Room> findAllRoomsByState(RoomState state) {
        TypedQuery<Room> query = this.em.createQuery("SELECT r FROM Room r WHERE room_state = :state", Room.class);
        query.setParameter("state", state.toString());
        return query.getResultList();
    }

    @Override
    public List<Room> findCategoryRoomsByState(String categoryName, RoomState state) {
        TypedQuery<Room> query = this.em.createQuery("SELECT r FROM Room r inner join Category c on category_id = c.id where c.name = :categoryName and room_state = :state", Room.class);
        query.setParameter("categoryName", categoryName);
        query.setParameter("state", state.toString());
        return query.getResultList();
    }

    @Override
    public OccupancyId nextIdentity() {
        return new OccupancyId(java.util.UUID.randomUUID().toString().toUpperCase());
    }


    @Override
    public void store(Category category) {
        this.em.persist(category);
    }

    @Override
    public void store(RoomOccupancy roomOccupancy) {
        this.em.persist(roomOccupancy);
    }
}
