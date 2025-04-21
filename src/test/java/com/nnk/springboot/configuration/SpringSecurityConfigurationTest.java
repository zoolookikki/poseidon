package com.nnk.springboot.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.nnk.springboot.config.CustomUserDetailsService;
import com.nnk.springboot.config.SpringSecurityConfiguration;
import com.nnk.springboot.controllers.BidController;
import com.nnk.springboot.controllers.LoginController;
import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.mapper.BidMapper;
import com.nnk.springboot.mapper.UserMapper;
import com.nnk.springboot.service.bid.IBidService;
import com.nnk.springboot.service.user.IUserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


// on ne teste que les contrôleurs qui sont dans la liste.
@WebMvcTest(controllers = { 
        LoginController.class,
        BidController.class,
        UserController.class
        })
// pour pouvoir les utiliser dans le test car Spring car @WebMvcTest ne les charge pas. 
@Import({SpringSecurityConfiguration.class, CustomUserDetailsService.class})
public class SpringSecurityConfigurationTest {

    // Pour que ces beans soient injectés dans le test pour pouvoir l’utiliser.
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;
    
    // mock pour le BidController.
    @MockitoBean
    private IBidService bidService;
    // ce bean dépend du service, il faut faire cela sinon : UnsatisfiedDependencyException: No qualifying bean of type 'BidMapper' available
    @MockitoBean
    private BidMapper bidMapper;
    
    // mock pour le UserController.
    @MockitoBean
    private IUserService userService;
    // ce bean dépend du service, il faut faire cela sinon : UnsatisfiedDependencyException: No qualifying bean of type 'UserMapper' available
    @MockitoBean
    private UserMapper userMapper;
    
    @BeforeEach
    void setup() {
        UserDetails user = User.withUsername("John")
                .password(passwordEncoder.encode("John@678"))
                .roles("USER")
                .build();
        // Fonction à implémenter car utilisée par Spring Security pour charger un utilisateur ==> ici simulée.
        when(customUserDetailsService.loadUserByUsername("John")).thenReturn(user);
    }

    private ResultActions performLogin(String username, String password) throws Exception {
        return mockMvc.perform(post("/login")
                .with(csrf())
                .param("username", username)
                .param("password", password));
    }
        
    // Ces tests doivent s'exécuter sans nécessiter d'authentification préalable.
    // --------------------------------------------------------------------------
    
    @Test
    @DisplayName("le chemin pour le login est accessible sans authentification")
    void pathForLoginSuccessTest() throws Exception {
        // when then
        mockMvc.perform(get("/login")).andExpect(status().isOk())
            .andExpect(view().name("login"));
    }
    
    @Test
    @DisplayName("les ressources statiques sont accessibles sans authentification")
    void pathForStaticRessourcesSuccessTest() throws Exception {
        // when then
        mockMvc.perform(get("/css/bootstrap.min.css")).andExpect(status().isOk());
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"/bidlist", "/zzzz"})    
    @DisplayName("l'accès à une page non autorisée ou inexistante d'un utilisateur non authentifié est redirigé vers la page de login")
    void pathForUnauthorizedAccessRedirectToLoginTest(String path) throws Exception {
        // when then
        // Vérifie que la redirection contient "/login" quel que soit la base de l'URL car andExpect(redirectedUrl("/login") n'est pas bon.
        mockMvc.perform(get(path)).andExpect(redirectedUrlPattern("**/login"));  
    }

    // Ces tests nécessitent une authentification.
    // -------------------------------------------
    
    @ParameterizedTest
    /*
    Pas besoin de faire les autres routes car elles sont identiques : /curvePoint/list, /rating/list, /trade/list, /rule/list.    
    Sinon il faut déclarer tous dans @WebMvcTest et ajouter les dépendances nécessaires avec @MockBean.
    */
    @ValueSource(strings = {"/bid/list"})
    // Avec @WithMockUser, Spring Security crée un utilisateur fictif qui est considéré comme identifié dans l'application. 
    @WithMockUser(username = "John", roles = "USER")
    @DisplayName("USER peut accéder à ses routes")
    void pathForUserRoleAccessTest(String path) throws Exception {
        // when then
        mockMvc.perform(get(path)).andExpect(status().isOk()); 
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"/user/list"})
    @WithMockUser(username = "John", roles = "USER")
    @DisplayName("USER ne peut pas accéder aux routes ADMIN")
    void pathForUserRoleAccessDeniedTest(String path) throws Exception {
        mockMvc.perform(get(path)).andExpect(status().isForbidden());
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"/bid/list", "/user/list"})
    @WithMockUser(username = "Admin", roles = "ADMIN")
    @DisplayName("ADMIN peut accéder à toutes les routes")
    void pathForadminRoleAccessTest(String path) throws Exception {
        mockMvc.perform(get(path)).andExpect(status().isOk());
    }    
    
    @Test
    @WithMockUser(username = "John", roles = "USER")
    @DisplayName("la déconnexion redirige vers login")
    void pathForLogoutSuccesTest() throws Exception {
        // when then
        mockMvc.perform(post("/app-logout")
                .with(csrf()))
                .andExpect(redirectedUrl("/login?logout"));
    }
    
    @Test
    @DisplayName("la connexion réussie redirige vers bid list")
    void loginSuccessForUserRedirectToBidListTest() throws Exception {
        // when then
        performLogin("John", "John@678").andExpect(redirectedUrl("/bid/list")); 
    }
    
    @Test
    @DisplayName("la connexion échouée redirige vers login avec erreur")
    void loginFailtForUserRedirectToLoginWithErrorTest() throws Exception {
        // when then
        performLogin("usernameNotExist", "Badpassword@123").andExpect(redirectedUrl("/login?error=true")); 
    }

}
