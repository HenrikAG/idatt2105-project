package ntnu.idatt2105.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Test class for Category class.
 */
public class CategoryTest {
    private Category category;
    
    @BeforeEach
    void setUp() {
        category = new Category("testname");
    }

    @Nested
    class GetterTests {

        @Test
        void getNameSHouldReturnCorrectCategoryname() {
            assertEquals("testname", category.getName());
        }
    }
}
