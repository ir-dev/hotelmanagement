package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.category.CategoryId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CategoryIdTest {
    @Test
    void given_3categoryId_2equals_1not_when_compare_then_2equals_1not() {
        // given
        CategoryId id0_1 = new CategoryId("0");
        CategoryId id0_2 = new CategoryId("0");
        CategoryId id1 = new CategoryId("1");

        // when...then
        assertNotEquals(id0_1, id1);
        assertNotEquals(id0_2, id1);
        assertEquals(id0_1, id0_2, "both CategoryIds should be equals");

        assertEquals("1", id1.getId());
    }
}
