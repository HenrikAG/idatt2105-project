package ntnu.idatt2105.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ntnu.idatt2105.backend.model.User;
import ntnu.idatt2105.backend.repository.UserRepository;

/**
 * Used by spring security to retrieve user data during auth.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    UserRepository userRepository;

    /**
     * Constructs a new CustomUserDetailsService.
     * 
     * @param userRepository the repository used to retrieve user data
     */
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by their username. Called by spring security.
     * 
     * @param username the username of the user
     * @return a CustomUserDetails object for the user
     * @throws UsernameNotFoundException if no user can be found with the provided username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new CustomUserDetails(user);
    }
}
