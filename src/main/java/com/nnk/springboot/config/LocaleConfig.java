package com.nnk.springboot.config;

import java.util.Locale;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * Configuration pour la gestion de l'internationalisation.
 * 
 * - Définit l'anglais comme langue par défaut au démarrage via SessionLocaleResolver.
 * - Permet de changer de langue dynamiquement en passant le paramètre `?lang=fr` dans l'URL.
 * - Enregistre un interceptor (LocaleChangeInterceptor) dans la chaîne de traitement des requêtes.
 * 
 * Exemple d'utilisation :
 * Accès à une page avec changement de langue :
 *    /home?lang=fr => passe l'application en français
 *    /home?lang=en => repasse en anglais
 */
@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        // force l'anglais au démarrage
        slr.setDefaultLocale(Locale.ENGLISH); 
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        // permet de changer via ?lang=fr
        interceptor.setParamName("lang"); 
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
