package ntnu.idatt2105.backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ntnu.idatt2105.backend.dto.ItemDTO;
import ntnu.idatt2105.backend.dto.LoginRequest;
import ntnu.idatt2105.backend.dto.LoginResponse;
import ntnu.idatt2105.backend.dto.UserDTO;
import ntnu.idatt2105.backend.dto.UserRegisterDTO;
import ntnu.idatt2105.backend.exception.AlreadyExistsException;
import ntnu.idatt2105.backend.security.JWTService;
import ntnu.idatt2105.backend.service.UserService;

/**
 * Handles HTTP requests related to users.
 */
@RestController
@Tag(name = "User", description = "Operations related to users")
@RequestMapping("/api/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JWTService jwtService;

    /**
     * Constructs a UserController.
     * 
     * @param userService a UserService to handle buisness logic regarding users.
     */
    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserService userService, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * Registers a new user if no user with the same username already exists.
     * 
     * @param registerDTO DTO containing the info of the user to be registered.
     * @return a ResponseEntity with info regarding the result of the request. Statuscode CREATED if successfull, CONFLICT otherwise.
     */
    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO registerDTO) {
        logger.info("Attempting to register user with username: " + registerDTO.getUsername());
        
        try {
            userService.addUser(registerDTO);
            return new ResponseEntity<>("User successfully registered", HttpStatus.CREATED);
        } catch (AlreadyExistsException exception) {
            logger.warn("There was an error registering a user with username: " + registerDTO.getUsername());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login to existing user")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Atempting to login with username: {}", loginRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        String token = jwtService.generateToken(authentication);
        LoginResponse response = new LoginResponse(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves all items from the categories the user has selected as favorites.
     * 
     * @param username the user's username
     * @return a ResponseEntity with a list of all items from the user's favotrite categories, 
     * or a ResponsEntity indicating a user was not found
     */
    @GetMapping("/{username}/favorite-items")
    @Operation(summary = "Get all of the items from the user's favorite categories")
    public ResponseEntity<?> getItemsFromFavoriteCategories(@PathVariable String username) {
        logger.info("Atempting to get favorite items");
        try {
            logger.info("Successfully got favorite items");
            List<ItemDTO> itemDTOs = userService.getItemsFromFavoriteCategories(username);
            return new ResponseEntity<>(itemDTOs, HttpStatus.OK);
        } catch (UsernameNotFoundException exception) {
            logger.warn("Error getting gavorite items: " + exception);
            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Returns all the items the user has listed.
     * 
     * @param username the username of the user
     * @return A ResponseEntity with a list of all the user's posted items,
     * or a message indicating the user was not found.
     */
    @GetMapping("/{username}/listed-items")
    @Operation(summary = "Get all the items the user has posted")
    public ResponseEntity<?> getPostedItems(@PathVariable String username) {
        try {
            List<ItemDTO> itemDTOs = userService.getListedItems(username);
            return new ResponseEntity<>(itemDTOs, HttpStatus.OK);
        } catch (UsernameNotFoundException exception) {
            return new ResponseEntity<>("User with username: " + username + " not found", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Returns all registered users.
     * 
     * @return a ResponseEntity with a list of all of the registered users.
     */
    @GetMapping("/all")
    @Operation(summary = "Get the username of all users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userService.getAllUsers();
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    /**
     * Deletes a user with the specified username.
     * 
     * @param username the username of the user to be deleted
     * @return ResponseEntity with the response of the request
     */
    @DeleteMapping(("/{username}"))
    @Operation(summary = "Delete a user")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        logger.info("Atempting to delete user with username: " + username);
        try {
            logger.info("Successfully deleted user");
            userService.deleteUser(username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UsernameNotFoundException exception) {
            logger.error("Failed deleting user", exception);
            return new ResponseEntity<>("User with username: " + username + " not found", HttpStatus.BAD_REQUEST);
        }
    }
}
