package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.stay.Invoice;
import at.fhv.hotelmanagement.domain.model.stay.StayId;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class InvoiceDTO {
    private GuestDTO guest;
    private String stayId;

    private String invoiceNo;
    private LocalDate createdDate;
    private LocalDate dueDate;
    private Integer nights;
    private String subTotalPerNight;
    private String subTotal;
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

    public String subTotalPerNight() {
        return this.subTotalPerNight;
    }

    public String subTotal() {
        return this.subTotal;
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
            this.instance.subTotalPerNight = String.valueOf(invoice.getSubTotalPerNight());
            this.instance.subTotal = String.valueOf(invoice.getSubTotal());
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
                Objects.requireNonNull(this.instance.subTotalPerNight, "subTotalPerNight must be set in InvoiceDTO");
                Objects.requireNonNull(this.instance.subTotal, "subTotal must be set in InvoiceDTO");
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
        return this.guest.equals(that.guest) && this.stayId.equals(that.stayId) && this.invoiceNo.equals(that.invoiceNo) && this.createdDate.equals(that.createdDate) && this.dueDate.equals(that.dueDate) && this.nights.equals(that.nights) && this.subTotalPerNight.equals(that.subTotalPerNight) && this.subTotal.equals(that.subTotal) && this.grandTotal.equals(that.grandTotal) && this.tax.equals(that.tax) && this.lineItems.equals(that.lineItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.guest, this.stayId, this.invoiceNo, this.createdDate, this.dueDate, this.nights, this.subTotalPerNight, this.subTotal, this.grandTotal, this.tax, this.lineItems);
    }
}
