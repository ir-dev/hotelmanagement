package at.fhv.hotelmanagement.domain.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;

public class Invoice {
    // generated hibernate id
    private Long id;
    private InvoiceId invoiceId;
    private Integer invoiceNo;
    private LocalDate createdDate;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private Integer nights;
    private Integer subTotal;
    private Double grandTotal;
    private Double tax;
    private Set<InvoiceLine> lineItems;


    // required for hibernate
    protected Invoice() {}

    protected Invoice(InvoiceId invoiceId, LocalDate arrivalDate,LocalDate departureDate) {
        this.invoiceId = invoiceId;
        this.createdDate = arrivalDate;
        this.dueDate = departureDate;
        this.nights = (int) DAYS.between(this.createdDate, this.dueDate);
    }

    //According to Mr. Thaler this method is still like a 'setter' -> needs to be adjusted
    public void addLineItem(InvoiceLine invoiceLine) {
        this.lineItems.add(invoiceLine);
        calculateSum();
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

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public LocalDate getPaidDate() {
        return this.paidDate;
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
