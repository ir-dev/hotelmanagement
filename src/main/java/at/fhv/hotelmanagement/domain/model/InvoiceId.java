package at.fhv.hotelmanagement.domain.model;

public class InvoiceId {
    private String id;

    // required for hibernate
    private InvoiceId() {}

    public InvoiceId(String invoiceId) {
        this.id = invoiceId;
    }

    public String getId() {
        return this.id;
    }
}
