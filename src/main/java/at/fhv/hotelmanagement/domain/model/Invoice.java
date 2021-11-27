package at.fhv.hotelmanagement.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class Invoice {
    // generated hibernate id
    private Long id;
    private InvoiceId invoiceId;
    private Map<String, Integer> selectedCategoriesRoomCount;
    private LocalDate contractDate;

    public Invoice() {
        this.invoiceId = new InvoiceId(java.util.UUID.randomUUID().toString().toUpperCase());
    }

    public InvoiceId getInvoiceId() {
        return this.invoiceId;
    }

    public LocalDate getContractDate() {
        return this.contractDate;
    }

    public Map<String, Integer> getSelectedCategoriesRoomCount() {
        return this.selectedCategoriesRoomCount;
    }

    public void calculate(Map<String, Integer> selectedCategoriesRoomCount) {
        this.selectedCategoriesRoomCount = selectedCategoriesRoomCount;
        this.contractDate = LocalDate.now();
    }

    public double subtotal() {
        return 0;
    }

    public double total() {
        return 0;
    }

    public double vat() {
        return 0;
    }
}
