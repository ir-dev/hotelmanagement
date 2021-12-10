package at.fhv.hotelmanagement.domain.model.stay;

import at.fhv.hotelmanagement.domain.model.Price;

public class InvoiceLine {
    // generated hibernate id
    private Long id;
    private ProductType type;
    private String product;
    private String description;
    private Integer quantity;
    private Price price;

    // required for hibernate
    private InvoiceLine() {};

    InvoiceLine(ProductType type, String product, String description, Integer quantity, Price price) {
        this.type = type;
        this.product = product;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public ProductType getType() {
        return this.type;
    }

    public String getProduct() {
        return this.product;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public Price getPrice() {
        return this.price;
    }

    public Price getTotalPrice() {
        return this.price.multiply(this.quantity);
    }
}
