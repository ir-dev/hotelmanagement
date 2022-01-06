package at.fhv.hotelmanagement.infrastructure;

import at.fhv.hotelmanagement.domain.model.stay.Invoice;
import at.fhv.hotelmanagement.domain.model.stay.InvoiceNo;
import at.fhv.hotelmanagement.domain.model.stay.Stay;
import at.fhv.hotelmanagement.domain.model.stay.StayId;
import at.fhv.hotelmanagement.domain.repositories.StayRepository;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Component
public class HibernateStayRepository implements StayRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public StayId nextIdentity() {
        Query query = this.em.createNativeQuery("select next value for seq_stayid");
        String key = query.getSingleResult().toString();
        return new StayId("S" + key);
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
    public Optional<Stay> findByInvoiceNo(InvoiceNo invoiceNo) {
        TypedQuery<Stay> query = this.em.createQuery("SELECT s FROM Stay s JOIN s.invoices si WHERE si.invoiceNo = :invoiceNo", Stay.class);
        query.setParameter("invoiceNo", invoiceNo);
        return query.getResultStream().findFirst();
    }

    @Override
    public Optional<Invoice> findInvoiceByInvoiceNo(InvoiceNo invoiceNo) {
        TypedQuery<Invoice> query = this.em.createQuery("FROM Invoice i WHERE i.invoiceNo = :invoiceNo", Invoice.class);
        query.setParameter("invoiceNo", invoiceNo);
        return query.getResultStream().findFirst();
    }

    @Override
    public String nextInvoiceSeq() {
        Query query = this.em.createNativeQuery("select next value for seq_invoiceno");
        String key = query.getSingleResult().toString();
        return key;
    }

    @Override
    public void store(Stay stay) {
        this.em.persist(stay);
    }
}
