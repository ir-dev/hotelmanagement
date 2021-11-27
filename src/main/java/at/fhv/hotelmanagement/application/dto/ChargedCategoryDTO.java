package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.Category;

import java.util.Objects;

public class ChargedCategoryDTO {
    private String name;
    private String description;
    private Integer singleOccupancy;
    private Integer multipleOccupancy;

    public static ChargedCategoryDTO.Builder builder() {
        return new ChargedCategoryDTO.Builder();
    }

    public String name() {
        return this.name;
    }

    public String description() {
        return this.description;
    }

    public Integer singleOccupancy() {
        return this.singleOccupancy;
    }

    public Integer multipleOccupancy() {
        return this.multipleOccupancy;
    }

    public static class Builder {
        private ChargedCategoryDTO instance;

        private Builder() {
            this.instance = new ChargedCategoryDTO();
        }

        public ChargedCategoryDTO.Builder withCategory(Category category) {
            this.instance.name = category.getName();
            this.instance.description = category.getDescription();
            this.instance.singleOccupancy = category.getPrice().getSingleOccupancy();
            if (category.getPrice().getMultipleOccupancy().isPresent()) {
                this.instance.multipleOccupancy = category.getPrice().getMultipleOccupancy().get();
            }
            return this;
        }


        public ChargedCategoryDTO build() {
            Objects.requireNonNull(this.instance.name, "name must be set in CategoryDTO");
            Objects.requireNonNull(this.instance.description, "description must be set in CategoryDTO");
            Objects.requireNonNull(this.instance.description, "availableRoomsCount must be set in CategoryDTO");

            return this.instance;
        }
    }
}
