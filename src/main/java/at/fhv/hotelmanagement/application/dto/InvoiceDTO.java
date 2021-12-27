package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.stay.Invoice;
import at.fhv.hotelmanagement.domain.model.stay.StayId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public class InvoiceDTO {
    private GuestDTO guest;
    private String stayId;
    private String invoiceNo;
    private LocalDate createdDate;
    private LocalDate dueDate;
    private Integer nights;
    private String discountRate;
    private String subTotalPerNight;
    private String subTotal;
    private String discountAmount;
    private String subTotalDiscounted;
    private String grandTotal;
    private String tax;
    private Set<InvoiceLineDTO> lineItems;

    public GuestDTO guest() {
        return this.guest;
    }

    public String stayId() { return this.stayId; }

    public String invoiceNo() {
        return this.invoiceNo;
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

    public String discountRate() {
        return this.discountRate;
    }

    public Boolean hasDiscount() {
        return !this.discountRate.equals("0");
    }

    public String subTotalPerNight() {
        return this.subTotalPerNight;
    }

    public String subTotal() {
        return this.subTotal;
    }

    public String discountAmount() {
        return this.discountAmount;
    }

    public String subTotalDiscounted() {
        return this.subTotalDiscounted;
    }

    public String grandTotal() {
        return this.grandTotal;
    }

    public String tax() {
        return this.tax;
    }

    public Set<InvoiceLineDTO> lineItems() {
        return this.lineItems;
    }

    public static InvoiceDTO.Builder builder() {
        return new InvoiceDTO.Builder();
    }

    public static class Builder {
        private InvoiceDTO instance;

        private Builder() {
            this.instance = new InvoiceDTO();
        }

        public Builder withStayId(StayId stayId) {
            this.instance.stayId = String.valueOf(stayId.getId());
            return this;
        }

        public Builder withGuestDTO(GuestDTO guest) {
            this.instance.guest = guest;
            return this;
        }

        public Builder withInvoiceEntity(Invoice invoice) {
            this.instance.invoiceNo = String.valueOf(invoice.getInvoiceNo().getNo());
            this.instance.createdDate = invoice.getCreatedDate();
            this.instance.dueDate = invoice.getDueDate();
            this.instance.nights = invoice.getNights();
            this.instance.discountRate = String.valueOf(invoice.getDiscountRate().multiply(BigDecimal.valueOf(100)).intValue());
            this.instance.subTotalPerNight = String.valueOf(invoice.getSubTotalPerNight());
            this.instance.subTotal = String.valueOf(invoice.getSubTotal());
            this.instance.discountAmount = String.valueOf(invoice.getDiscountAmount());
            this.instance.subTotalDiscounted = String.valueOf(invoice.getSubTotalDiscounted());
            this.instance.grandTotal = String.valueOf(invoice.getGrandTotal());
            this.instance.tax = String.valueOf(invoice.getTax());
            return this;
        }

        public Builder withLineItemsDTO(Set<InvoiceLineDTO> lineItems) {
            this.instance.lineItems = lineItems;
            return this;
        }

        public InvoiceDTO build() {
            Objects.requireNonNull(this.instance.stayId, "stayId must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.guest, "guest must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.invoiceNo, "invoiceNo must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.lineItems, "lineItems must be set in InvoiceDTO");

            if (this.instance.lineItems.size() > 0) {
                Objects.requireNonNull(this.instance.createdDate, "createdDate must be set in InvoiceDTO");
                Objects.requireNonNull(this.instance.dueDate, "dueDate must be set in InvoiceDTO");
                Objects.requireNonNull(this.instance.discountRate, "discountRate must be set in InvoiceDTO");
                Objects.requireNonNull(this.instance.subTotalPerNight, "subTotalPerNight must be set in InvoiceDTO");
                Objects.requireNonNull(this.instance.subTotal, "subTotal must be set in InvoiceDTO");
                Objects.requireNonNull(this.instance.discountAmount, "discountedAmount must be set in InvoiceDTO");
                Objects.requireNonNull(this.instance.subTotalDiscounted, "subTotalDiscounted must be set in InvoiceDTO");
                Objects.requireNonNull(this.instance.grandTotal, "grandTotal must be set in InvoiceDTO");
                Objects.requireNonNull(this.instance.tax, "tax must be set in InvoiceDTO");
            }
            return this.instance;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InvoiceDTO that = (InvoiceDTO) o;
        return Objects.equals(this.guest, that.guest) && Objects.equals(this.stayId, that.stayId) && Objects.equals(this.invoiceNo, that.invoiceNo) && Objects.equals(this.createdDate, that.createdDate) && Objects.equals(this.dueDate, that.dueDate) && Objects.equals(this.nights, that.nights) && Objects.equals(this.discountRate, that.discountRate) && Objects.equals(this.subTotalPerNight, that.subTotalPerNight) && Objects.equals(this.subTotal, that.subTotal) && Objects.equals(this.discountAmount, that.discountAmount) && Objects.equals(this.subTotalDiscounted, that.subTotalDiscounted) && Objects.equals(this.grandTotal, that.grandTotal) && Objects.equals(this.tax, that.tax) && Objects.equals(this.lineItems, that.lineItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.guest, this.stayId, this.invoiceNo, this.createdDate, this.dueDate, this.nights, this.discountRate, this.subTotalPerNight, this.subTotal, this.discountAmount, this.subTotalDiscounted, this.grandTotal, this.tax, this.lineItems);
    }
}
