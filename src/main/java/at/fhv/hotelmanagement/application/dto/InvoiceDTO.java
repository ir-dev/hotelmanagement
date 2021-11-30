package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class InvoiceDTO {
    private String invoiceId;
    private Map<ChargedCategoryDTO, Integer> selectedCategoriesRoomCount;
    private LocalDate contractDate;
    private GuestDTO guest;

    public static InvoiceDTO.Builder builder() {
        return new InvoiceDTO.Builder();
    }

    public String invoiceId() {
        return this.invoiceId;
    }

    public Map<ChargedCategoryDTO, Integer> selectedCategoriesRoomCount() {
        return this.selectedCategoriesRoomCount;
    }

    public LocalDate contractDate() {
        return this.contractDate;
    }

    public GuestDTO guest() {
        return this.guest;
    }

    public Double subtotal() {
        double subtotal = 0;
        for(Map.Entry<ChargedCategoryDTO, Integer> selectedCategory : this.selectedCategoriesRoomCount.entrySet()) {
            subtotal += selectedCategory.getKey().halfBoard() * selectedCategory.getValue();
        }
        return subtotal;
    }

    public double vat() {
        return subtotal() * 0.1;
    }

    public double total() {
        return subtotal() + vat();
    }


    public static class Builder {
        private InvoiceDTO instance;

        private Builder() {
            this.instance = new InvoiceDTO();
        }

        public Builder withInvoiceId(InvoiceId invoiceId) {
            this.instance.invoiceId = invoiceId.getId();
            return this;
        }

        public Builder withSelectedCategoriesRoomCount(Map<ChargedCategoryDTO, Integer> selectedCategoriesRoomCount) {
            this.instance.selectedCategoriesRoomCount = selectedCategoriesRoomCount;
            return this;
        }

        public Builder withContractDate(LocalDate contractDate) {
            this.instance.contractDate = contractDate;
            return this;
        }

        public Builder withGuestDTO(GuestDTO guest) {
            this.instance.guest = guest;
            return this;
        }

        public InvoiceDTO build() {
            Objects.requireNonNull(this.instance.invoiceId, "id must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.selectedCategoriesRoomCount, "selectedCategoriesRoomCount must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.contractDate, "contractDate must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.guest, "guest must be set in InvoiceDTO");
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
        return Objects.equals(this.invoiceId, that.invoiceId) && Objects.equals(this.selectedCategoriesRoomCount, that.selectedCategoriesRoomCount) && Objects.equals(this.contractDate, that.contractDate) && Objects.equals(this.guest, that.guest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.invoiceId, this.selectedCategoriesRoomCount, this.contractDate, this.guest);
    }
}
