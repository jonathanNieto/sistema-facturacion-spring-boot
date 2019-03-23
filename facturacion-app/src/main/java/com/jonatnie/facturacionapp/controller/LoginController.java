package com.jonatnie.facturacionapp.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * LoginController
 */
@Controller
public class LoginController {

    @GetMapping(value="/login")
    public String login(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        if (principal != null) {
            redirectAttributes.addFlashAttribute("info", "Ya ha iniciado sesión anteriormente");
            model.addAttribute("title", "Inicio de sesión");
            return "redirect:/";
        }
        return "login";
    }
    
    
}