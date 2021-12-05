package at.fhv.hotelmanagement.domain.model.stay;


public class InvoiceLine {
    // generated hibernate id
    private Long id;
    private String product;
    private String description;
    private Integer quantity;
    private Integer price;

    // required for hibernate
    private InvoiceLine() {};

    public InvoiceLine(String product, String description, Integer quantity, Integer price) {
        this.product = product;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
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

    public Integer  getPrice() {
        return this.price;
    }
}
