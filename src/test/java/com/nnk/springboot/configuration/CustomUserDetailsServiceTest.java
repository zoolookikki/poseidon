package com.nnk.springboot.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nnk.springboot.config.CustomUserDetailsService;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

/*
Permet à JUnit 5 d’activer le support de Mockito dans ce test.
Pour créer un mock avec @Mock.
Pour injecter automatiquement ces mocks avec @InjectMocks.
Ici Spring Boot ne gère pas les beans comme avec @SpringBootTest et @WebMvcTest => c'est un test unitaire "pur".
*/
@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("loadUserByUsername : utilisateur trouvé")
    void loadUserByUsernameOkTest() {
        // given
        User user = new User(1, "John", "$2y$10$YIJhlEJyi73CNd3hjPSX8OECdXFc7U3BtvHmDbkqTkhIhT5HFxYBq", "Full John", "USER");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        // when
        // UserDetails est une interface de Spring Security qui représente un utilisateur authentifié.
        UserDetails result = customUserDetailsService.loadUserByUsername(user.getUsername());

        // then
        /* l'objet result contient :
            - Le nom d’utilisateur
            - Le mot de passe (hashé) 
            - Les rôles de l’utilisateur
        */
        assertThat(result.getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getPassword()).isEqualTo(user.getPassword());
        assertThat(result.getAuthorities()).extracting("authority").containsExactly("ROLE_USER");
    }

    @Test
    @DisplayName("loadUserByUsername : utilisateur non trouvé")
    void loadUserByUsernameFailTest() {
        // given
        when(userRepository.findByUsername("John")).thenReturn(Optional.empty());

        // when/then
        assertThatThrownBy(() -> customUserDetailsService.loadUserByUsername("John"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("User not found");
    }
}
