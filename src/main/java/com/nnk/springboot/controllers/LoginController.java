package com.nnk.springboot.controllers;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class LoginController {

    /**
     * Displays the login page.
     *
     * @return The "login" view which displays the login form.
     */
    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        Locale locale = RequestContextUtils.getLocale(request);
        log.debug("GetMapping/login,locale="+locale);

        return "login";
    }

}
