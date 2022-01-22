package at.fhv.hotelmanagement.view.rest.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class CreateBookingResponse {
    private static final String STATUS_OK = "ok";
    private static final String STATUS_ERROR = "error";

    private final String status;
    private final String message;

    private CreateBookingResponse() {
        this.status = STATUS_OK;
        this.message = null;
    }

    private CreateBookingResponse(String msg) {
        this.status = STATUS_ERROR;
        this.message = msg;
    }

    public static CreateBookingResponse success() {
        return new CreateBookingResponse();
    }

    public static CreateBookingResponse error(Exception e) {
        return new CreateBookingResponse(e.getMessage());
    }

    @JsonProperty(required = true)
    public String status() {
        return this.status;
    }

    @JsonProperty
    public String message() {
        return this.message;
    }
}
