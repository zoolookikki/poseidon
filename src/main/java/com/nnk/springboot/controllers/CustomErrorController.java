package com.nnk.springboot.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Custom controller to intercept standard Spring Boot error routes and provide user-friendly responses using Thymeleaf templates
 * <p>Handles the following HTTP status codes with custom messages:
 * <ul>
 *     <li><b>403</b>: Access denied</li>
 *     <li><b>404</b>: Page not found</li>
 *     <li><b>500</b>: Internal server error (message from exception if available)</li>
 *     <li><b>Default</b>: Any other status returns a message if available</li>
 * </ul>
 * </p>
 * <p>Also includes endpoints to simulate common errors for testing purposes.</p>
 */
@Controller
@Log4j2
public class CustomErrorController implements ErrorController {

    /**
     * Handles all errors intercepted by Spring Boot and redirects to a Thymeleaf error page.
     *
     * @param request the HTTP request object containing error attributes
     * @param model   the model used to pass the error message to the view
     * @return the name of the Thymeleaf error template
     */
    // @RequestMapping au lieu de @GetMapping car j'ai eu un cas (tiny) ou j'ai reçu l'erreur 405 en post.
    // @GetMapping("/error")
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object errorStatusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String customMessage = "An unexpected error occurred";
        int status = -1;

        if (errorStatusCode != null) {
            status = Integer.parseInt(errorStatusCode.toString());
            customMessage = switch (status) {
                case 403 -> "Access denied";
                case 404 -> "Page not found";
                case 500 -> {
                    Object errorException = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
                    if (errorException instanceof Throwable throwable && throwable.getMessage() != null) {
                        yield throwable.getMessage();
                    }
                    yield "Internal server error";
                }
                default -> {
                    String errorMessage = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
                    if (errorMessage != null && !errorMessage.isBlank()) {
                        yield errorMessage;
                    }
                    yield "An unexpected error occurred (status " + status + ")";
                }
            };
        }

        log.error("handleError - status: {}, message: {}", status, customMessage);
        model.addAttribute("errorMessage", customMessage);

        return "error";
    }

    // pour tester l'erreur 500.
    @GetMapping("/traptest")
    public String traptest() {
        throw new RuntimeException("Simulated internal server error");
    }

    // 405 pour tester le cas default.
    @PostMapping("/gettestfail")
    public String getTestFail() {
        return "";
    }

}
