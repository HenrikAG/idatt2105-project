package ntnu.idatt2105.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * Constructs a UserService.
     * 
     * @param userRepository a repository to access and register user data.
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        //TODO Add check for admin password and set role to admin.
        Role userRole = Role.USER;

        User newUser = new User();
        newUser.setUsername(userRegisterInfo.getUsername());
        newUser.setPassword(userRegisterInfo.getPassword());
        newUser.setRole(userRole);

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
}
