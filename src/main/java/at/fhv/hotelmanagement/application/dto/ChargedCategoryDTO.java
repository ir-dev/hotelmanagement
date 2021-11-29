package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.Category;

import java.util.Objects;

public class ChargedCategoryDTO {
    private String name;
    private String description;
    //Price
    private Integer halfBoard;
    private Integer fullBoard;

    public static ChargedCategoryDTO.Builder builder() {
        return new ChargedCategoryDTO.Builder();
    }

    public String name() {
        return this.name;
    }

    public String description() {
        return this.description;
    }

    public Integer halfBoard() {
        return this.halfBoard;
    }

    public Integer fullBoard() {
        return this.fullBoard;
    }

    public static class Builder {
        private ChargedCategoryDTO instance;

        private Builder() {
            this.instance = new ChargedCategoryDTO();
        }

        public ChargedCategoryDTO.Builder withCategory(Category category) {
            this.instance.name = category.getName();
            this.instance.description = category.getDescription();
            this.instance.halfBoard = category.getPrice().getHalfBoard();
            this.instance.fullBoard = category.getPrice().getFullBoard();
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
