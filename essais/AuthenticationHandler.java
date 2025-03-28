package com.nnk.springboot.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Spring Security interface that allows you to customize what happens after a successful login.
 */
@Component
public class AuthenticationHandler implements AuthenticationSuccessHandler {

   /*
   Méthode à implémenter appelée après une connexion réussie.
   HttpServletRequest request représente la requête HTTP en cours.
   HttpServletResponse response : permet d'envoyer une réponse au navigateur.
   Authentication authentication : contient les détails de l'utilisateur authentifié (nom, rôles, etc.)
   */
   @Override 
   public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException  {

      // Récupère l'URL initiale avant le login
      SavedRequest savedRequest = (SavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
      String redirectUrl = (savedRequest != null) ? savedRequest.getRedirectUrl() : null;

      // Si un admin avait tenté d'accéder à /user/list => cas non ergonomique du create one dans home.html...
      if (redirectUrl != null && redirectUrl.contains("/user/list")) {
         for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
               response.sendRedirect("/user/list");
               return;
            }
         }
      }

      // Redirection par défaut dans tous les autres cas
      response.sendRedirect("/bidList/list");

   }
}
