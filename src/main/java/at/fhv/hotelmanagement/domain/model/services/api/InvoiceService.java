package at.fhv.hotelmanagement.domain.model.services.api;

import at.fhv.hotelmanagement.domain.model.Category;
import at.fhv.hotelmanagement.domain.model.Stay;

import java.util.List;

public interface InvoiceService {

    void composeInvoice(Stay stay, List<Category> categories, boolean transactional);
}
