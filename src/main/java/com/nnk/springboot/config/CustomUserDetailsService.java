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
 */
/*
Il implémente UserDetailsService, une interface de Spring Security qui permet d’authentifier les utilisateurs sur la base des informations contenues dans une base de données.
!! ATTENTION : La configuration de Spring Security doit prendre en compte cette classe via un AuthenticationManager (voir SpringSecurityConfiguration.java).
*/
@Service
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    // pas d'@Autowired pour pouvoir faire le test unitaire sinon compliqué.
    private final UserRepository userRepository;
    
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }    

    // Crée une liste d’autorités => Cette méthode transforme le rôle de l’utilisateur en une autorité compréhensible par Spring Security
    private List<SimpleGrantedAuthority> getGrantedAuthorities(String role) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        log.debug("Authorities: " + authorities);
        return authorities;
    }

    // Fonction à implémenter utilisée par Spring Security pour charger un utilisateur.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // e-mail toujours en minuscule.
        log.debug("Recherche de l'utilisateur = {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.debug("Aucun utilisateur trouvé");
                    return new UsernameNotFoundException("User not found : " + username);
                });
        log.debug("Utilisateur trouvé : {}", user);

        /*
        Construit un objet User (de Spring Security) contenant :
            - Le nom d’utilisateur
            - Le mot de passe (hashé) 
            - Les rôles de l’utilisateur
        Le retour est un objet UserDetails, qui est une interface utilisée par Spring Security pour représenter un utilisateur authentifié.            
        */
       // new org.springframework.security.core.userdetails.User pour éviter confusion avec model.User.
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getGrantedAuthorities(user.getRole())
        );
    }
}
