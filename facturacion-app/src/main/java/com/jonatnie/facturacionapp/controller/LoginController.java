package com.jonatnie.facturacionapp.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * LoginController
 */
@Controller
public class LoginController {

    @GetMapping(value = "/login")
    public String login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout, Model model, Principal principal,
            RedirectAttributes redirectAttributes) {
        if (principal != null) {
            redirectAttributes.addFlashAttribute("info", "Ya ha iniciado sesión anteriormente");
            return "redirect:/";
        }
        if (error != null) {
            model.addAttribute("error", "Hay un error por favor verifique sus datos");
        }
        if (logout != null) {
            model.addAttribute("success", "Ha cerrado sesión con éxito");
        }
        model.addAttribute("title", "Inicio de sesión");
        return "login";
    }

}