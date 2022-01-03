package at.fhv.hotelmanagement.domain.repositories;

import at.fhv.hotelmanagement.domain.model.stay.Invoice;
import at.fhv.hotelmanagement.domain.model.stay.InvoiceNo;
import at.fhv.hotelmanagement.domain.model.stay.Stay;
import at.fhv.hotelmanagement.domain.model.stay.StayId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StayRepository {
    StayId nextIdentity();

    List<Stay> findAll();

    Optional<Stay> findById(StayId stayId);

    Optional<Stay> findByInvoiceNo(InvoiceNo invoiceNo);

    Optional<Invoice> findInvoiceByInvoiceNo(InvoiceNo invoiceNo);

    String nextInvoiceSeq();

    void store(Stay stay);
}
