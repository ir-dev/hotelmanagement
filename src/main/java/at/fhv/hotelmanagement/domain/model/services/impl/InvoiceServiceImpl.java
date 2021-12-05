package at.fhv.hotelmanagement.domain.model.services.impl;
import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.services.api.InvoiceService;
import at.fhv.hotelmanagement.domain.model.stay.Invoice;
import at.fhv.hotelmanagement.domain.model.stay.InvoiceLine;
import at.fhv.hotelmanagement.domain.model.stay.Stay;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public class InvoiceServiceImpl implements InvoiceService {

    private List<Category> categories;

    @Override
    public void composeInvoice(Stay stay, List<Category> categories, boolean transactional) {
        Invoice invoice = stay.getInvoice();
        this.categories = categories;

        //Add every claimed product to the invoice
        Map<String, Integer> selectedCategoriesRoomCount = stay.getSelectedCategoriesRoomCount();
        for(Map.Entry<String, Integer> scrc : selectedCategoriesRoomCount.entrySet()) {
            if(scrc.getValue() > 0) {
                invoice.addLineItem(new InvoiceLine(scrc.getKey(), fetchCategoryDesc(scrc.getKey()),scrc.getValue(), fetchCategoryPrice(scrc.getKey())));
            }
        }
        if(transactional) {
            invoice.close();
        }
    }


    private Integer fetchCategoryPrice(String product) {
        for(Category c: this.categories) {
            if(c.getName().equals(product)) {
                return c.getPrice().getHalfBoard();
            }
        }
        return null;
    }

    private String fetchCategoryDesc(String product) {
        for(Category c: this.categories) {
            if(c.getName().equals(product)) {
                return c.getDescription();
            }
        }
        return null;
    }

}
