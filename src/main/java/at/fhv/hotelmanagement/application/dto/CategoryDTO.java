package at.fhv.hotelmanagement.application.dto;

import java.util.Objects;

public class CategoryDTO {
    private String name;
    private String description;
    // private List<Room> roomList;
//    private SeasonPrice seasonPrice;

    public static CategoryDTO.Builder builder() {
        return new CategoryDTO.Builder();
    }

    public String name() {
        return this.name;
    }

    public String description() {
        return this.description;
    }

//    public SeasonPrice seasonPrice() {
//        return this.seasonPrice;
//    }

    private CategoryDTO() {
    }

    public static class Builder {
        private CategoryDTO instance;

        private Builder() {
            this.instance = new CategoryDTO();
        }

        public CategoryDTO.Builder withName(String name) {
            this.instance.name = name;
            return this;
        }

        public CategoryDTO.Builder withDescription(String description) {
            this.instance.description = description;
            return this;
        }

//        public CategoryDTO.Builder withSeasonPrice(SeasonPrice seasonPrice) {
//            this.instance.seasonPrice = seasonPrice;
//            return this;
//        }

        public CategoryDTO build() {
            Objects.requireNonNull(this.instance.name, "name must be set in CategoryDTO");
            Objects.requireNonNull(this.instance.description, "firstName must be set in CategoryDTO");
//            Objects.requireNonNull(this.instance.seasonPrice, "lastName must be set in CategoryDTO");

            return this.instance;
        }

    }
}
