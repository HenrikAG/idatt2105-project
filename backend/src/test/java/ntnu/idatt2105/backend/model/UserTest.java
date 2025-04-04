package ntnu.idatt2105.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Test class for the User class.
 */
public class UserTest {
    private User user;
    
    @BeforeEach
    void setUp() {
        user = new User("testname", "testpassword");
    }

    @Nested
    class GetterTests {

        @Test
        void getUserNameShouldGetCorrectUsername() {
            assertEquals("testname", user.getUsername());
        }

        @Test
        void getPasswordShouldReturnCorrectPassword() {
            assertEquals("testpassword", user.getPassword());
        }
    }
}
