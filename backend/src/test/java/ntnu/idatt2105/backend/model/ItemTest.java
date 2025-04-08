package ntnu.idatt2105.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Test class that tests the Item class.
 */
public class ItemTest {
    private Item item;
    private Item emptyItem;

    private final String expectedName = "testItem";
    private final double expectedPrice = 9.99;
    private final Category expectedCategory = new Category("testCategory");
    private final String expectedDescription = "description";
    private final String expectedImageName = "test.jpg";
    private final User seller = new User();

    @BeforeEach
    void setUp() {
        item = new Item(expectedName, expectedPrice, expectedCategory, expectedDescription, expectedImageName, seller);
        emptyItem = new Item();
    }

    @Nested
    class ConstructorTests {

        @Test
        void shouldCreateItem() {
            assertNotNull(item);
        }

        @Test
        void emptyConstructorShouldCreateItem() {
            assertNotNull(emptyItem);
        }
    }

    @Nested
    class GetterTests {

        @Test
        void shouldGetCorrectName() {
            assertEquals(expectedName, item.getName());
        }

        @Test
        void shouldGetCorrectPrice() {
            assertEquals(expectedPrice, item.getPrice());
        }

        @Test
        void shouldGetCorrectCategory() {
            assertEquals(expectedCategory, item.getCategory());
        }

        @Test
        void shouldGetCorrectDescription() {
            assertEquals(expectedDescription, item.getDescription());
        }

        @Test
        void shouldGetCorrectImageName() {
            assertEquals(expectedImageName, item.getImageName());
        }
    }
}
