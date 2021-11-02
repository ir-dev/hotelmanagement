package at.fhv.hotelmanagement.application.dto;

import java.time.LocalDate;
import java.util.Objects;

public class GuestDTO {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public static GuestDTO.Builder builder(){
        return new GuestDTO.Builder();
    }

    public String guestId() {
        return this.id;
    }

    public String firstName() {
        return this.firstName;
    }

    public String lastName() {
        return this.lastName;
    }

    public String fullName() {
        return this.firstName + this.lastName;
    }

    private GuestDTO() {

    }

    public static class Builder {
        private GuestDTO instance;

        private Builder() {
            this.instance = new GuestDTO();
        }

        public Builder withId(String id) {
            this.instance.id = id;
            return this;
        }

        public Builder withFirstName(String firstName){
            this.instance.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName){
            this.instance.lastName = lastName;
            return this;
        }

        public GuestDTO build(){
            Objects.requireNonNull(this.instance.id, "id must be set in GuestDTO");
            Objects.requireNonNull(this.instance.firstName, "firstName must be set in GuestDTO");
            Objects.requireNonNull(this.instance.lastName, "lastName must be set in GuestDTO");

            return this.instance;
        }
    }
}
