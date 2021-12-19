package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.stay.InvoiceLine;

import java.util.Objects;

public class InvoiceLineDTO {
    private String type;
    private String product;
    private String description;
    private Integer quantity;
    private String price;
    private String totalPrice;

    public String type() {
        return this.type;
    }

    public String product() {
        return this.product;
    }

    public Integer quantity() {
        return this.quantity;
    }

    public String description() {
        return this.description;
    }

    public String price() {
        return this.price;
    }

    public String totalPrice() { return this.totalPrice; }

    public static InvoiceLineDTO.Builder builder() {
        return new InvoiceLineDTO.Builder();
    }

    public static class Builder {
        private InvoiceLineDTO instance;

        private Builder() {
            this.instance = new InvoiceLineDTO();
        }

        public Builder withInvoiceLineEntity(InvoiceLine invoiceLine) {
            this.instance.type = String.valueOf(invoiceLine.getType());
            this.instance.product = invoiceLine.getProduct();
            this.instance.description = invoiceLine.getDescription();
            this.instance.quantity = invoiceLine.getQuantity();
            this.instance.price = String.valueOf(invoiceLine.getPrice());
            this.instance.totalPrice = String.valueOf(invoiceLine.getTotalPrice());
            return this;
        }


        public InvoiceLineDTO build() {
            Objects.requireNonNull(this.instance.type, "type must be set in InvoiceLineDTO");
            Objects.requireNonNull(this.instance.product, "product must be set in InvoiceLineDTO");
            Objects.requireNonNull(this.instance.description, "description must be set in InvoiceLineDTO");
            Objects.requireNonNull(this.instance.quantity, "quantity must be set in InvoiceLineDTO");
            Objects.requireNonNull(this.instance.price, "price must be set in InvoiceLineDTO");
            Objects.requireNonNull(this.instance.totalPrice, "totalPrice must be set in InvoiceLineDTO");

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
        InvoiceLineDTO that = (InvoiceLineDTO) o;
        return Objects.equals(this.type, that.type) && Objects.equals(this.product, that.product) && Objects.equals(this.description, that.description) && Objects.equals(this.quantity, that.quantity) && Objects.equals(this.price, that.price) && Objects.equals(this.totalPrice, that.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.product, this.description, this.quantity, this.price, this.totalPrice);
    }
}
