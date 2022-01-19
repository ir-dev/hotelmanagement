package at.fhv.hotelmanagement.application.dto;

import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.view.rest.serializer.PriceSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.boot.jackson.JsonComponent;

import java.util.Objects;

@JsonComponent
public final class AvailableCategoryDTO {
    private String name;
    private String description;
    private Integer availableRoomsCount;
    private Price price;
    private String imageUrl;

    public static AvailableCategoryDTO.Builder builder() {
        return new AvailableCategoryDTO.Builder();
    }

    @JsonProperty(required = true)
    public String name() {
        return this.name;
    }

    @JsonProperty(required = true)
    public String description() {
        return this.description;
    }

    @JsonProperty(required = true)
    @JsonSerialize(using = PriceSerializer.class)
    public Price price() {
        return this.price;
    }

    @JsonProperty(required = true)
    public String imageUrl() {
        return this.imageUrl;
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

        public AvailableCategoryDTO.Builder withPrice(Price price) {
            this.instance.price = price;
            return this;
        }

        public AvailableCategoryDTO.Builder withImageUrl(String imageUrl) {
            this.instance.imageUrl = imageUrl;
            return this;
        }

        public AvailableCategoryDTO build() {
            Objects.requireNonNull(this.instance.name, "name must be set in AvailableCategoryDTO");
            Objects.requireNonNull(this.instance.description, "description must be set in AvailableCategoryDTO");
            Objects.requireNonNull(this.instance.availableRoomsCount, "availableRoomsCount must be set in AvailableCategoryDTO");
            Objects.requireNonNull(this.instance.price, "price must be set in AvailableCategoryDTO");
            Objects.requireNonNull(this.instance.imageUrl, "imageUrl must be set in AvailableCategoryDTO");

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
        return this.name.equals(that.name) && this.description.equals(that.description) && this.availableRoomsCount.equals(that.availableRoomsCount) && this.price.equals(that.price) && this.imageUrl.equals(that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.description, this.availableRoomsCount, this.price, this.imageUrl);
    }
}
