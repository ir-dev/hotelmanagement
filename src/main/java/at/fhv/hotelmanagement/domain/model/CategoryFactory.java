package at.fhv.hotelmanagement.domain.model;

public class CategoryFactory {

    public static Category createCategory(CategoryId categoryId,
                                          String name,
                                          String description,
                                          Integer maxPersons) {

        return new Category(
                categoryId,
                name,
                description,
                maxPersons
        );
    }
}