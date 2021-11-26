package at.fhv.hotelmanagement.domain.model;

public class CategoryFactory {

    public static Category createCategory(String name,
                                          String description,
                                          Integer maxPersons) {

        return new Category(
                name,
                description,
                maxPersons
        );
    }
}