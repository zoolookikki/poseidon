package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String login() {
        log.debug("GetMapping/login");

        return "login";
    }

}
