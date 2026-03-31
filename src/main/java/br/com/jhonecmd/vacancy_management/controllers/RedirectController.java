package br.com.jhonecmd.vacancy_management.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/")
public class RedirectController {
    @GetMapping("")
    public RedirectView redirectToSwagger() {
        return new RedirectView("/swagger-ui/index.html");
    }
}
