package at.fhv.hotelmanagement.application.dto;

import java.util.Map;
import java.util.Objects;

public final class RoomDTO {
    private String roomNumber;
    private String roomState;

    public static RoomDTO.Builder builder() {
        return new RoomDTO.Builder();
    }

    public String number() {
        return this.roomNumber;
    }

    public String state() {
        return this.roomState;
    }

    private RoomDTO() {
    }

    public static class Builder {
        private RoomDTO instance;

        private Builder() {
            this.instance = new RoomDTO();
        }

        public Builder withNumber(String roomNumber) {
            this.instance.roomNumber = roomNumber;
            return this;
        }

        public Builder withState(String roomState) {
            this.instance.roomState = roomState;
            return this;
        }

        public RoomDTO build() {
            Objects.requireNonNull(this.instance.roomNumber, "roomNumber must be set in RoomDTO");
            Objects.requireNonNull(this.instance.roomState, "roomNumber must be set in RoomDTO");

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
        RoomDTO roomDTO = (RoomDTO) o;
        return Objects.equals(this.roomNumber, roomDTO.roomNumber) && Objects.equals(this.roomState, roomDTO.roomState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.roomNumber, this.roomState);
    }
}
