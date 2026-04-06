package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller to redirect root path to Swagger UI.
 */
@Controller
public class RootController {

    /**
     * Redirects the root path to Swagger UI.
     *
     * @return redirect to Swagger UI
     */
    @GetMapping("/")
    public String redirectToSwagger() {
        return "redirect:/swagger-ui/index.html";
    }
}
