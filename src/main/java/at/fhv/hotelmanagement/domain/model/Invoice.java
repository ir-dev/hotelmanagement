package at.fhv.hotelmanagement.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Invoice {
    // generated hibernate id
    private Long id;
    private InvoiceId invoiceId;
    private StayId stayId;
    private LocalDate currentDate;
    private LocalTime currentTime;

    public static Invoice create(InvoiceId invoiceId, StayId stayId) {
        return new Invoice(invoiceId, stayId);
    }

    // required for hibernate
    private Invoice() {}

    private Invoice(InvoiceId invoiceId, StayId stayId) {
        this.invoiceId = invoiceId;
        this.stayId = stayId;
        this.currentDate = LocalDate.now();
        this.currentTime = LocalTime.now();
    }

    public InvoiceId getInvoiceId() {
        return this.invoiceId;
    }

    public StayId getStayId() {
        return this.stayId;
    }

    public LocalDate getCurrentDate() {
        return this.currentDate;
    }

    public LocalTime getCurrentTime() {
        return this.currentTime;
    }
}
