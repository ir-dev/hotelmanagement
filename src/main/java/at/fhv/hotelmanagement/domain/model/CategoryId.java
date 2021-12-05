package at.fhv.hotelmanagement.domain.model;

import java.util.Objects;

public class CategoryId {
    private String id;

    // required for hibernate
    private CategoryId() {
    }

    public CategoryId(String categoryId) {
        this.id = categoryId;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CategoryId that = (CategoryId) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
