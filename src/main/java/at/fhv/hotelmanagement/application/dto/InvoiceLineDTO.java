package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.stay.InvoiceLine;


import java.util.Objects;

public class InvoiceLineDTO {
    private String product;
    private String description;
    private Integer quantity;
    private Integer price;

    public String product() {
        return this.product;
    }

    public Integer quantity() {
        return this.quantity;
    }

    public String description() {
        return this.description;
    }

    public Integer  price() {
        return this.price;
    }

    public static InvoiceLineDTO.Builder builder() {
        return new InvoiceLineDTO.Builder();
    }

    public static class Builder {
        private InvoiceLineDTO instance;

        private Builder() {
            this.instance = new InvoiceLineDTO();
        }

        public Builder withInvoiceLineEntity(InvoiceLine invoiceLine) {
            this.instance.product = invoiceLine.getProduct();
            this.instance.description = invoiceLine.getDescription();
            this.instance.quantity = invoiceLine.getQuantity();
            this.instance.price = invoiceLine.getPrice();
            return this;
        }


        public InvoiceLineDTO build() {
            Objects.requireNonNull(this.instance.product, "product must be set in InvoiceLineDTO");
            Objects.requireNonNull(this.instance.quantity, "quantity must be set in InvoiceLineDTO");
            Objects.requireNonNull(this.instance.price, "price must be set in InvoiceLineDTO");

            return this.instance;
        }
    }
}
