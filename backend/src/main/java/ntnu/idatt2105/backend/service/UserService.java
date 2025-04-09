package ntnu.idatt2105.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ntnu.idatt2105.backend.dto.ItemDTO;
import ntnu.idatt2105.backend.dto.UserDTO;
import ntnu.idatt2105.backend.dto.UserRegisterDTO;
import ntnu.idatt2105.backend.enums.Role;
import ntnu.idatt2105.backend.exception.AlreadyExistsException;
import ntnu.idatt2105.backend.model.User;
import ntnu.idatt2105.backend.repository.UserRepository;

/**
 * Service class for Users. Handles business logic for User entities.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a UserService.
     * 
     * @param userRepository a repository to access and register user data
     * @param passwordEncoder encoder to encode user passwords
     */
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Adds a new user.
     * 
     * @param userRegisterInfo DTO with the details of the new user
     * @return A DTO representing the added user
     */
    public UserDTO addUser(UserRegisterDTO userRegisterInfo) {
        if (userExists(userRegisterInfo.getUsername())) {
            throw new AlreadyExistsException("User with username already exists. Username: " + userRegisterInfo.getUsername());
        }

        User newUser = new User();
        newUser.setUsername(userRegisterInfo.getUsername());
        newUser.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
        newUser.setRole(Role.USER);

        return new UserDTO(userRepository.save(newUser));
    }

    /**
     * Checks if a user with the given username exists.
     * 
     * @param username username to check
     * @return true if a user with the given username already exists, false otherwise
     */
    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    /**
     * Returns a list of all the items from a user's favorite categories.
     * 
     * @param username the username of the user
     * @return list of all the items from the user's favorite categories
     */
    public List<ItemDTO> getItemsFromFavoriteCategories(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not not found with username" + username));

        List<ItemDTO> favItems = user.getFavoriteCategories().stream()
            .flatMap(category -> category.getItems().stream())
            .map(ItemDTO::new)
            .collect(Collectors.toList());

        return favItems;
    }

    /**
     * Returns a list of all the registered users as UserDTOs.
     * 
     * @return a list of all registered users as UserDTOs
     */
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
            .map(UserDTO::new)
            .collect(Collectors.toList());
    }
}
