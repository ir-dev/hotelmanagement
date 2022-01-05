package at.fhv.hotelmanagement.application.dto;

import java.util.Map;
import java.util.Objects;

public final class CategoryDTO {
    private String name;
    private Map<String, String> rooms;

    public static CategoryDTO.Builder builder() {
        return new CategoryDTO.Builder();
    }

    public String name() {
        return this.name;
    }

    public Map<String, String> rooms() {
        return this.rooms;
    }

    private CategoryDTO() {
    }

    public static class Builder {
        private CategoryDTO instance;

        private Builder() {
            this.instance = new CategoryDTO();
        }

        public Builder withName(String name) {
            this.instance.name = name;
            return this;
        }

        public Builder withRooms(Map<String, String> rooms) {
            this.instance.rooms = rooms;
            return this;
        }

        public CategoryDTO build() {
            Objects.requireNonNull(this.instance.name, "name must be set in CategoryDTO");
            Objects.requireNonNull(this.instance.rooms, "rooms must be set in CategoryDTO");

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
        CategoryDTO that = (CategoryDTO) o;
        return Objects.equals(this.name, that.name) && Objects.equals(this.rooms, that.rooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.rooms);
    }
}
