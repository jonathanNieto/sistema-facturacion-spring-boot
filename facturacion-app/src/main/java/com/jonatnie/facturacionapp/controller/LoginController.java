package com.jonatnie.facturacionapp.controller;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

    @Autowired
    private MessageSource messageSource;

    @GetMapping(value = "/login")
    public String login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout, Model model, Principal principal,
            RedirectAttributes redirectAttributes, Locale locale) {
        if (principal != null) {
            redirectAttributes.addFlashAttribute("info", messageSource.getMessage("flash.attribute.login.controller.logi.info", null, locale));
            return "redirect:/";
        }
        if (error != null) {
            model.addAttribute("error", messageSource.getMessage("model.attribute.login.controller.login.error", null, locale));
        }
        if (logout != null) {
            model.addAttribute("success", messageSource.getMessage("model.attribute.login.controller.login.success", null, locale));
        }
        model.addAttribute("title", messageSource.getMessage("model.attribute.login.controller.login.title", null, locale));
        return "login";
    }

}