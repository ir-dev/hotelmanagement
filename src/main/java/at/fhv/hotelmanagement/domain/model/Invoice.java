package at.fhv.hotelmanagement.domain.model;

import java.time.LocalDate;
import java.util.Map;

public class Invoice {
    // generated hibernate id
    private Long id;
    private InvoiceId invoiceId;
    private LocalDate contractDate;

    public Invoice() {
        this.invoiceId = new InvoiceId(java.util.UUID.randomUUID().toString().toUpperCase());
    }

    public InvoiceId getInvoiceId() {
        return this.invoiceId;
    }

    public LocalDate getContractDate() {
        this.contractDate = LocalDate.now();
        return this.contractDate;
    }


}
