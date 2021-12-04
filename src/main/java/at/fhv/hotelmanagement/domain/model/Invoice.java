package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.enums.InvoiceState;

import java.time.LocalDate;
import java.util.Set;
import static java.time.temporal.ChronoUnit.DAYS;

public class Invoice {
    // generated hibernate id
    private Long id;
    private InvoiceId invoiceId;
    private Integer invoiceNo;
    private InvoiceState invoiceState;
    private LocalDate createdDate;
    private LocalDate dueDate;
    private Integer nights;
    private Integer subTotal;
    private Double grandTotal;
    private Double tax;
    private Set<InvoiceLine> lineItems;

    private static Integer counter = 10000;

    // required for hibernate
    protected Invoice() {}

    protected Invoice(InvoiceId invoiceId, LocalDate arrivalDate,LocalDate departureDate) {
        this.invoiceId = invoiceId;
        this.createdDate = arrivalDate;
        this.dueDate = departureDate;
        this.nights = (int) DAYS.between(this.createdDate, this.dueDate);
        this.invoiceState = InvoiceState.PENDING;
    }

    //According to Mr. Thaler this method is still like a 'setter' -> needs to be adjusted
    public void addLineItem(InvoiceLine invoiceLine) {
        this.lineItems.add(invoiceLine);
        calculateSum();
    }

    public void close() {
        if (this.invoiceState == InvoiceState.PENDING) {
            this.invoiceState = InvoiceState.CONFIRMED;
            this.invoiceNo = counter++;
        } else {
            throw new IllegalStateException("Only invoice with PENDING status can be closed.");
        }
    }


    private void calculateSum() {
        int sum = 0;
        for(InvoiceLine lineItem : this.lineItems) {
            sum += lineItem.getPrice() * lineItem.getQuantity();
        }
        this.subTotal = sum * this.nights;
        this.tax = this.subTotal * 0.1;
        this.grandTotal = this.subTotal + this.tax;
    }


    public InvoiceId getInvoiceId() {
        return this.invoiceId;
    }

    public Integer getInvoiceNo() {
        return this.invoiceNo;
    }

    public InvoiceState getInvoiceState() {
        return this.invoiceState;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public Integer getNights() {
        return this.nights;
    }

    public Integer getSubTotal() {
        return this.subTotal;
    }

    public Double getGrandTotal() {
        return this.grandTotal;
    }

    public Double getTax() {
        return this.tax;
    }

    public Set<InvoiceLine> getLineItems() {
        return this.lineItems;
    }
}
