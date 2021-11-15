package at.fhv.hotelmanagement.domain.infrastructure;

import at.fhv.hotelmanagement.domain.model.Category;
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
    public boolean exists(String categoryName) {
        Optional<Category> category = findByName(categoryName);
        if(category.isPresent()){
            return true;
        }
        return false;
    }

    @Override
    public void store(Category category) throws AlreadyExistsStoreException {
        this.em.persist(category);
    }
}
