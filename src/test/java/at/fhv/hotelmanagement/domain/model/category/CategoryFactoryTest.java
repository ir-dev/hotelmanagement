package at.fhv.hotelmanagement.domain.model.category;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.category.CategoryFactory;
import at.fhv.hotelmanagement.domain.model.category.CategoryId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryFactoryTest extends AbstractTest {
    @Test
    void given_categorydetails_when_createcategoryfromfactory_then_detailsequals() {
        //given
        CategoryId categoryId = new CategoryId("1");
        String name = ("Honeymoon Suite DZ");
        String description = ("description");
        Integer maxPersons = 2;
        Price p1 = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Price p2 = Price.of(BigDecimal.ONE, Currency.getInstance("EUR"));

        //when
        Category category = CategoryFactory.createCategory(categoryId, name, description, maxPersons, p1, p2);

        //then
        assertEquals(category.getCategoryId(), categoryId);
        assertEquals(category.getName(), name);
        assertEquals(category.getDescription(), description);
        assertEquals(category.getMaxPersons(), maxPersons);
        assertEquals(category.getHalfBoardPrice(), p1);
        assertEquals(category.getFullBoardPrice(), p2);
    }
}
