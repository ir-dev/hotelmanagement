package at.fhv.hotelmanagement.domain.model.services.api;

import at.fhv.hotelmanagement.domain.model.Stay;

public interface InvoiceService {

    void calculateInvoice(Stay stay);
}
