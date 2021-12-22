package at.fhv.hotelmanagement.domain.model.category;

import at.fhv.hotelmanagement.domain.model.Price;

public class CategoryFactory {
    private CategoryFactory() {}

    public static Category createCategory(CategoryId categoryId,
                                          String name,
                                          String description,
                                          Integer maxPersons,
                                          Price halfBoardPrice,
                                          Price fullBoardPrice) {

        return new Category(
                categoryId,
                name,
                description,
                maxPersons,
                halfBoardPrice,
                fullBoardPrice
        );
    }
}