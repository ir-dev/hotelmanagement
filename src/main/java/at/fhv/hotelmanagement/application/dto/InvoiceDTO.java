package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.stay.Invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public class InvoiceDTO {
    private String invoiceNo;
    private String invoiceState;
    private LocalDate createdDate;
    private LocalDate dueDate;
    private Integer nights;
    private Integer subTotal;
    private Double grandTotal;
    private Double tax;
    private Set<InvoiceLineDTO> lineItems;
    private GuestDTO guest;


    public String invoiceNo() {
        return this.invoiceNo;
    }

    public String invoiceState() {
        return this.invoiceState;
    }

    public LocalDate createdDate() {
        return this.createdDate;
    }

    public LocalDate currentDate() {
        return LocalDate.now();
    }

    public LocalDate dueDate() {
        return this.dueDate;
    }

    public Integer nights() {
        return this.nights;
    }

    public Integer subTotal() {
        return this.subTotal;
    }

    public Double grandTotal() {
        return this.grandTotal;
    }

    public Double tax() {
        return this.tax;
    }

    public Set<InvoiceLineDTO> lineItems() {
        return this.lineItems;
    }

    public GuestDTO guest() {
        return this.guest;
    }

    public static InvoiceDTO.Builder builder() {
        return new InvoiceDTO.Builder();
    }

    public static class Builder {
        private InvoiceDTO instance;

        private Builder() {
            this.instance = new InvoiceDTO();
        }

        public Builder withInvoiceEntity(Invoice invoice) {
            this.instance.invoiceNo = invoice.getInvoiceNo();
            this.instance.invoiceState = String.valueOf(invoice.getInvoiceState());
            this.instance.createdDate = invoice.getCreatedDate();
            this.instance.dueDate = invoice.getDueDate();
            this.instance.nights = invoice.getNights();
            this.instance.subTotal = invoice.getSubTotal();
            this.instance.grandTotal = invoice.getGrandTotal();
            this.instance.tax = invoice.getTax();
            return this;
        }

        public Builder withLineItemsDTO(Set<InvoiceLineDTO> lineItems) {
            this.instance.lineItems = lineItems;
            return this;
        }


        public Builder withGuestDTO(GuestDTO guest) {
            this.instance.guest = guest;
            return this;
        }

        public InvoiceDTO build() {
            Objects.requireNonNull(this.instance.createdDate, "createdDate must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.subTotal, "subTotal must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.grandTotal, "grandTotal must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.tax, "tax must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.lineItems, "lineItems must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.guest, "guest must be set in InvoiceDTO");
            return this.instance;
        }
    }


}
