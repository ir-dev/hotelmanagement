package at.fhv.hotelmanagement.domain.model.services.impl;


import at.fhv.hotelmanagement.domain.model.*;
import at.fhv.hotelmanagement.domain.model.services.api.InvoiceService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class InvoiceServiceImpl implements InvoiceService {

    @Override
    public void composeInvoice(Stay stay, List<Category> categories) {
        Map<String, Integer> selectedCategoriesRoomCount = stay.getSelectedCategoriesRoomCount();
        Invoice invoice = stay.getInvoice();



    }

}
