package ntnu.idatt2105.backend.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ntnu.idatt2105.backend.model.User;

/**
 * Custom implementation of UserDetails. Used to authenticate and authorize users.
 */
public class CustomUserDetails implements UserDetails {
    private final User user;

    /**
     * Constructs a new CustomUserDetails with the given user.
     * 
     * @param user the user to wrap
     */
    public CustomUserDetails(User user) {
        this.user = user;
    }

    /**
     * Retyrbs the authorities granted to a user. In this app it only returns the users role.
     * 
     * @return a collection with the user's role
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
    }

    /**
     * Returns the password used to authenticate the user.
     * 
     * @return the user's password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the username used to authenticate the user.
     * 
     * @return the user's username
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

}
