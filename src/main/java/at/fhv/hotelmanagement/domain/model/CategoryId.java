package at.fhv.hotelmanagement.domain.model;

public class CategoryId {
    private String id;

    //required for hibernate
    private CategoryId() {}

    public CategoryId(String categoryId) {this.id = categoryId;}

    public String getId() {return this.id;}
}
