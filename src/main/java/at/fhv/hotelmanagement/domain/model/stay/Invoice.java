package at.fhv.hotelmanagement.domain.model.stay;

import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.PriceCurrencyMismatchException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Currency;
import java.util.Set;
import static java.time.temporal.ChronoUnit.DAYS;

public class Invoice {
    // generated hibernate id
    private Long id;
    private InvoiceNo invoiceNo;
    private LocalDate createdDate;
    private LocalDate dueDate;
    private Integer nights;
    private Price subTotalPerNight;
    private Price subTotal;
    private BigDecimal discountRate;
    private Price discountAmount;
    private Price subTotalDiscounted;
    private Price grandTotal;
    private Price tax;
    private Set<InvoiceLine> lineItems;
    private InvoiceRecipient invoiceRecipient;
    private double taxRate;

    // required for hibernate
    private Invoice() {}

    Invoice(Set<InvoiceLine> lineItems, LocalDate arrivalDate, LocalDate departureDate, BigDecimal discountRate, double taxRate, InvoiceRecipient invoiceRecipient) throws PriceCurrencyMismatchException {
        this(new InvoiceNo("0"), lineItems, arrivalDate, departureDate, discountRate, taxRate, invoiceRecipient);
    }

    Invoice(InvoiceNo invoiceNo, Set<InvoiceLine> lineItems, LocalDate arrivalDate, LocalDate departureDate, BigDecimal discountRate, double taxRate, InvoiceRecipient invoiceRecipient) throws PriceCurrencyMismatchException {
        this.invoiceNo = invoiceNo;
        this.lineItems = lineItems;
        this.nights = (int) DAYS.between(arrivalDate, departureDate);
        this.createdDate = LocalDate.now();
        this.dueDate = departureDate;
        this.discountRate = discountRate;
        this.invoiceRecipient = invoiceRecipient;
        this.taxRate = taxRate;

        if (lineItems.size() > 0) {
            determinePrices();
        } else {
            this.subTotalPerNight = this.subTotal = this.discountAmount = this.subTotalDiscounted = this.tax = this.grandTotal = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        }
    }

    private void determinePrices() throws PriceCurrencyMismatchException {
        Price sum = Price.of(BigDecimal.ZERO, this.lineItems.stream().findFirst().orElseThrow().getPrice().getCurrency());

        for (InvoiceLine lineItem : this.lineItems) {
            sum = sum.add(lineItem.getPrice().multiply(lineItem.getQuantity()));
        }

        this.subTotalPerNight = sum;
        this.subTotal = sum.multiply(this.nights);
        this.discountAmount = this.subTotal.multiply(this.discountRate);
        this.subTotalDiscounted = this.subTotal.add(this.discountAmount.multiply(BigDecimal.valueOf(-1)));
        this.tax = this.subTotalDiscounted.multiply(BigDecimal.valueOf(this.taxRate));
        this.grandTotal = this.subTotalDiscounted.add(this.tax);
    }

    public InvoiceNo getInvoiceNo() {
        return this.invoiceNo;
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

    public Price getSubTotalPerNight() {
        return this.subTotalPerNight;
    }

    public BigDecimal getDiscountRate() {
        return this.discountRate;
    }

    public Price getDiscountAmount() {
        return this.discountAmount;
    }

    public Price getSubTotalDiscounted() {
        return this.subTotalDiscounted;
    }

    public Price getSubTotal() {
        return this.subTotal;
    }

    public Price getGrandTotal() {
        return this.grandTotal;
    }

    public Price getTax() {
        return this.tax;
    }

    public Set<InvoiceLine> getLineItems() {return Collections.unmodifiableSet(this.lineItems);}

    public InvoiceRecipient getInvoiceRecipient() {return this.invoiceRecipient;}
}
