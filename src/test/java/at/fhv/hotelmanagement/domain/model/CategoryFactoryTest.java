package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.enums.Country;
import at.fhv.hotelmanagement.domain.model.enums.RoomState;
import at.fhv.hotelmanagement.domain.model.enums.Salutation;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryFactoryTest {
    @Test
    void given_categorydetails_when_createcategoryfromfactory_then_detailsequals() {
        //given
        CategoryId categoryId = new CategoryId("1");
        String name = ("Honeymoon Suite DZ");
        String description = ("description");
        Integer maxPersons = 2;

        //when
        Category category = CategoryFactory.createCategory(categoryId, name, description, maxPersons);

        //then
        assertEquals(category.getCategoryId(), categoryId);
        assertEquals(category.getName(), name);
        assertEquals(category.getDescription(), description);
        assertEquals(category.getMaxPersons(), maxPersons);
    }
}
