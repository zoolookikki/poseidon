package com.nnk.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuring application security with Spring Security.
 * <p>
 * This class defines authentication and authorization rules by specifying access to different routes and user management methods.
 * </p>
 *
 * <p>
 * Key features include:
 * <ul>
 *     <li>Definition of access authorizations</li>
 *     <li>Customizing authentication and redirection after login</li>
 *     <li>Managing disconnection and session invalidation</li>
 *     <li>Configuring Password Encryption</li>
 * </ul>
 * </p>
 */

// @Configuration indique que c'est une classe conteneur de beans géré par Spring (voir commentaire à propos de @Bean). 
@Configuration
//Active Spring Security et applique une configuration personnalisée.
@EnableWebSecurity 
public class SpringSecurityConfiguration {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /*
    @Bean est automatiquement détecté et exécuté par Spring lors du démarrage de l’application ==>
    Dans le démarrage de Spring Security, Spring cherche une configuration personnalisée.
    Il regarde dans le contexte Spring s’il y a un @Bean de type SecurityFilterChain.
    S’il en trouve un, il l’applique.
    S’il n’en trouve pas, il applique une configuration de sécurité par défaut (tout est protégé).
   */
    @Bean
    // SecurityFilterChain => un ensemble de règles de sécurité : collection de filtres de sécurité.
    /*
     HttpSecurity => permet de définir :
         - Qui peut accéder à quelles routes (authorizeHttpRequests())
         - Comment s’authentifier (formLogin(), httpBasic(), etc.)
         - Comment se déconnecter (logout())
         - Gestion des sessions (sessionManagement())
    */
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                /*
                Permet de configurer les autorisations d'accès aux différentes routes.
                Définit qui peut accéder à quelles URLs, en fonction des rôles utilisateurs.
                */
                .authorizeHttpRequests(auth -> {
                    /*
                    Autoriser l'accès à la racine et à la page de login sans authentification.
                    Autoriser les fichiers statiques.
                    */
                    auth.requestMatchers("/login", "/favicon.ico", "/images/**", "/css/**", "/js/**").permitAll();
                    // Seuls les utilisateurs ayant le rôle ADMIN peuvent y accéder.
                    auth.requestMatchers("/user/**").hasRole("ADMIN");
                    /*
                    Toute autre requête nécessite une connexion, mais sans restriction de rôle spécifique.
                    Un utilisateur sans connexion sera redirigé vers la page de login.
                    */
                    auth.anyRequest().authenticated();
                })
                // Pages de connexion/déconnexion personnalisées.
                .formLogin(form -> form
                        // la page personnalisée.
                        .loginPage("/login")
                        .defaultSuccessUrl("/bidList/list", true)
                        .failureUrl("/login?error=true")
                        // permet de définir un gestionnaire personnalisé qui sera exécuté après une connexion réussie afin de rediriger dynamiquement l'utilisateur selon son rôle.
                        // n'est plus utilisée pour le moment ==> voir remarque sur la méthode authenticationSuccessHandler.
                        //.successHandler(authenticationSuccessHandler())
                        // Tout le monde peut y accéder
                        .permitAll()
                )       
                .logout(logout -> logout
                        // URL pour se déconnecter => d'habitude logout mais partout est mis app-logout.
                        .logoutUrl("/app-logout")
                        // Redirige vers la page de login après la déconnexion
                        .logoutSuccessUrl("/login?logout")
                        // Invalide la session
                        .invalidateHttpSession(true)
                        // Supprime le cookie de session
                        .deleteCookies("JSESSIONID")
                        // Tout le monde peut y accéder                        
                        .permitAll() 
                )
                // permet de rediriger les erreurs 403 (acces denied) vers ErrorController car Spring Security intercepte l'erreur ==> ne va pas dans GlobalExceptionHandler.
                //.exceptionHandling(exception -> exception.accessDeniedPage("/error/403"))
                
                // Construit et applique la configuration de sécurité.
                .build();
    }

    /*
    Cette méthode permet d’indiquer à Spring Security d’utiliser la classe CustomUserDetailsService pour authentifier des utilisateurs
    Cette méthode définit un AuthenticationManager personnalisé dans Spring Security
    Un AuthenticationManager est le composant principal qui gère l’authentification des utilisateurs en validant leur nom d’utilisateur et leur mot de passe.
    Spring utilisera ce AuthenticationManager pour l’authentification des utilisateurs.
        HttpSecurity http : Permet d’accéder aux objets de configuration de sécurité Spring
        BCryptPasswordEncoder bCryptPasswordEncoder : Utilisé pour vérifier les mots de passe hashés stockés en base de données.
    */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        // Récupère un AuthenticationManagerBuilder à partir de l’objet HttpSecurity => Récupère l'objet pour construire l’authentification.
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        /*
        Définit la source des utilisateurs (UserDetailsService) et l’encodeur de mot de passe (BCrypt).
        Utilise CustomUserDetailsService pour charger les utilisateurs depuis la base de données.
            - userDetailsService(customUserDetailsService)
                customUserDetailsService est une classe qui implémente UserDetailsService.
                Cela signifie que l’authentification se fait en consultant la base de données.
            - passwordEncoder(bCryptPasswordEncoder)
                Vérifie que le mot de passe fourni par l’utilisateur correspond à celui hashé en base.
                Spring Security compare le hash du mot de passe entré avec celui stocké en base.
        */
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
        /*
        Construit l’objet AuthenticationManager et le retourne.
        Ce AuthenticationManager sera utilisé par Spring Security pour authentifier les utilisateurs.
        */
        return authenticationManagerBuilder.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // permet de personnaliser ce qui se passe après une connexion réussie.
    // n'est plus utile pour le moment (classe AuthentificationHandler supprimée).
    /*
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationHandler();
    }
    */

}
