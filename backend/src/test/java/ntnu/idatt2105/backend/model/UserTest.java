package ntnu.idatt2105.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import ntnu.idatt2105.backend.enums.Role;

/**
 * Test class for the User class.
 */
public class UserTest {
    private User user;
    
    @BeforeEach
    void setUp() {
        user = new User("testname", "testpassword", Role.USER);
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

        @Test
        void getRoleShouldReturnCorrectRole() {
            assertEquals(Role.USER, user.getRole());
            assertNotEquals(Role.ADMIN, user.getRole());
        }
    }
}
