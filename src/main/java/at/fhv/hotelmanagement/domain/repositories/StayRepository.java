package at.fhv.hotelmanagement.domain.repositories;

import at.fhv.hotelmanagement.domain.model.stay.*;

import java.util.List;
import java.util.Optional;

public interface StayRepository {
    StayId nextIdentity();

    List<Stay> findAll();

    Optional<Stay> findById(StayId stayId);

    Optional<Stay> findByInvoiceNo(InvoiceNo invoiceNo);

    Optional<Invoice> findInvoiceByInvoiceNo(InvoiceNo invoiceNo);

    String nextInvoiceSeq();

    void store(Stay stay);

    void storeRecipient(InvoiceRecipient invoiceRecipient);

    Optional<InvoiceRecipient> findRecipientById(Long id);
}
