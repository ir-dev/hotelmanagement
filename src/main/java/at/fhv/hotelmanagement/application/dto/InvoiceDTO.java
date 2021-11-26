package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class InvoiceDTO {
    private String invoiceId;
    private LocalDate currentDate;
    private LocalTime currentTime;

    public static InvoiceDTO.Builder builder() {
        return new InvoiceDTO.Builder();
    }

    public String getInvoiceId() {
        return this.invoiceId;
    }

    public LocalDate getCurrentDate() {
        return this.currentDate;
    }

    public LocalTime getCurrentTime() {
        return this.currentTime;
    }

    public static class Builder {
        private InvoiceDTO instance;

        private Builder() {
            this.instance = new InvoiceDTO();
        }

        public Builder withInvoiceEntity(Invoice invoice) {
            this.instance.invoiceId = invoice.getInvoiceId().getId();
            this.instance.currentDate = invoice.getCurrentDate();
            this.instance.currentTime = invoice.getCurrentTime();
            return this;
        }

        public InvoiceDTO build() {
            Objects.requireNonNull(this.instance.invoiceId, "id must be set in InvoiceDTO");
            return this.instance;
        }
    }
}
