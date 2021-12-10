package at.fhv.hotelmanagement.domain.model.stay;

import java.util.Objects;

public class InvoiceNo {
    private String no;

    // required for hibernate
    private InvoiceNo() {}

    public InvoiceNo(String invoiceNo) {
        this.no = invoiceNo;
    }

    public String getNo() {
        return this.no;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InvoiceNo invoiceNo = (InvoiceNo) o;
        return this.no.equals(invoiceNo.no);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.no);
    }
}
