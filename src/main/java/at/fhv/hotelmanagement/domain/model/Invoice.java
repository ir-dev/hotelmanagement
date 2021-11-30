package at.fhv.hotelmanagement.domain.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class Invoice {
    // generated hibernate id
    private Long id;
    private InvoiceId invoiceId;
    private LocalDate contractDate;

    protected Invoice(InvoiceId invoiceId) {
        this.invoiceId = invoiceId;
    }

    public InvoiceId getInvoiceId() {
        return this.invoiceId;
    }

    public LocalDate getContractDate() {
        this.contractDate = LocalDate.now();
        return this.contractDate;
    }


}
