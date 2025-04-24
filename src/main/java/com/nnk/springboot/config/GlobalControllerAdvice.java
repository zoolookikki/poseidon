package com.nnk.springboot.config;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/*
@ControllerAdvice permet de définir un contrôleur global, qui s’applique à tous les contrôleurs (@Controller) de l'application.
Dans notre cas c'est utilse pour afficher le nom de l'utilisateur en permanence.
*/
@ControllerAdvice
public class GlobalControllerAdvice {

    /* 
    @ModelAttribute permet de déclarer cette méthode afin qu'elle soit exécutée avant chaque appel à un contrôleur,afin d'ajouter l'attribut username à la vue Thymeleaf. 
    */
    @ModelAttribute
    public void addCommonAttributes(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }
    }
}
