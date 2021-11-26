package at.fhv.hotelmanagement.application.dto;

import java.util.Objects;

public final class AvailableCategoryDTO {
    private String name;
    private String description;
    private Integer availableRoomsCount;

    public static AvailableCategoryDTO.Builder builder() {
        return new AvailableCategoryDTO.Builder();
    }

    public String name() {
        return this.name;
    }

    public String description() {
        return this.description;
    }

    public Integer availableRoomsCount() {
        return this.availableRoomsCount;
    }

    private AvailableCategoryDTO() {
    }

    public static class Builder {
        private AvailableCategoryDTO instance;

        private Builder() {
            this.instance = new AvailableCategoryDTO();
        }

        public AvailableCategoryDTO.Builder withName(String name) {
            this.instance.name = name;
            return this;
        }

        public AvailableCategoryDTO.Builder withDescription(String description) {
            this.instance.description = description;
            return this;
        }

        public AvailableCategoryDTO.Builder withAvailableRoomsCount(Integer availableRoomsCount) {
            this.instance.availableRoomsCount = availableRoomsCount;
            return this;
        }

        public AvailableCategoryDTO build() {
            Objects.requireNonNull(this.instance.name, "name must be set in CategoryDTO");
            Objects.requireNonNull(this.instance.description, "description must be set in CategoryDTO");
            Objects.requireNonNull(this.instance.description, "availableRoomsCount must be set in CategoryDTO");

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
        AvailableCategoryDTO that = (AvailableCategoryDTO) o;
        return Objects.equals(this.name, that.name) && Objects.equals(this.description, that.description) && Objects.equals(this.availableRoomsCount, that.availableRoomsCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.description, this.availableRoomsCount);
    }
}
