package com.example.spring.data.rest.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class RedirectController {
    @GetMapping("/")
    public RedirectView redirectLogin(){
        return new RedirectView("/login.html");
    }
}
