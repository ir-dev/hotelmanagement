package at.fhv.hotelmanagement.view.rest.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.jackson.JsonComponent;

import java.util.Objects;

@JsonComponent
public class BookingResponse {
    private static final String STATUS_OK = "ok";
    private static final String STATUS_ERROR = "error";

    private final String status;
    private final String message;

    private BookingResponse() {
        this.status = STATUS_OK;
        this.message = null;
    }

    private BookingResponse(String msg) {
        this.status = STATUS_ERROR;
        this.message = msg;
    }

    public static BookingResponse success() {
        return new BookingResponse();
    }

    public static BookingResponse error(Exception e) {
        return new BookingResponse(e.getMessage());
    }

    @JsonProperty(required = true)
    public String status() {
        return this.status;
    }

    @JsonProperty
    public String message() {
        return this.message;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookingResponse that = (BookingResponse) o;
        return Objects.equals(this.status, that.status) && Objects.equals(this.message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.status, this.message);
    }
}
