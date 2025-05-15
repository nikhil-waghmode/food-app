package com.capgemini.food_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/")
    public String homePage() {
        return "index"; // Loads index.html
    }
}
