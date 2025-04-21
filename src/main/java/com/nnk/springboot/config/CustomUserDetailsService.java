package com.nnk.springboot.config;

import java.util.ArrayList;
import java.util.List;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

/**
 * This service loads users from the database and integrates them with Spring Security.
 *
 * <p>
 * This class implements UserDetailsService, an interface used by Spring Security to load a user's details from the database.
 * </p>
 *
 */
@Service
@Log4j2
// Cette classe doit être prise en compte dans la configuration de sécurité, notamment via un authentication.AuthenticationManager (voir SpringSecurityConfiguration.java).
public class CustomUserDetailsService implements UserDetailsService {

    // pas d'@Autowired pour pouvoir faire le test unitaire sinon compliqué.
    private final UserRepository userRepository;
    
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }    

    /**
     * Creates a list of Spring Security authorities from the user's role.
     *
     * <p>
     * This method converts a role (eg: "ADMIN") into an authority usable by Spring Security (eg: "ROLE_ADMIN").
     * </p>
     *
     * @param role the role of the user
     * @return a list of authorities corresponding to the role
     */
    private List<SimpleGrantedAuthority> getGrantedAuthorities(String role) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        log.debug("Authorities: " + authorities);
        return authorities;
    }

    /**
     * Loads a user by their username.
     *
     * <p>
     * This method is automatically called by Spring Security during authentication.
     * It searches for the user in the database, then returns a userdetails object containing:
     * <ul>
     *     <li>The username</li>
     *     <li>The hashed password</li>
     *     <li>The list of authorities regarding user roles</li>
     * </ul>
     * </p>
     *
     * @param username the username
     * @return a UserDetails object representing the authenticated user
     * @throws UsernameNotFoundException if the user is not found in the database
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Recherche de l'utilisateur = {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.debug("Aucun utilisateur trouvé");
                    return new UsernameNotFoundException("User not found : " + username);
                });
        log.debug("Utilisateur trouvé : {}", user);

       // new org.springframework.security.core.userdetails.User pour éviter confusion avec model.User.
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getGrantedAuthorities(user.getRole())
        );
    }
}
