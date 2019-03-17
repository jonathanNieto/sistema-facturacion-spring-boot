package com.jonatnie.facturacionapp.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.jonatnie.facturacionapp.model.entity.Client;
import com.jonatnie.facturacionapp.model.service.IClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * ClientController
 */
@Controller
@SessionAttributes("client")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Client> clientList = clientService.findAll();
        model.addAttribute("title", "Listado de clientes");
        model.addAttribute("clients", clientList);
        return "list";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String create(Map<String, Object> model) {
        Client client = new Client();
        model.put("client", client);
        model.put("title", "Formulario Cliente");
        return "form";
    }

    @RequestMapping(value = "/form/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model,
            RedirectAttributes redirectAttributes) {

        Client client = null;

        if (id > 0) {
            client = clientService.findOne(id);
            if (client == null) {
                redirectAttributes.addFlashAttribute("error", "Cliente no encontrado");
                return "redirect:/list";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Cliente no encontrado");
            return "redirect:/list";
        }
        model.put("title", "Editando Cliente");
        model.put("client", client);
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String save(@Valid Client client, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes, SessionStatus sessionStatus) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Formulario Cliente");
            return "form";
        }
        String messageFlash = (client.getId() == null) ? "Cliente creado con éxito." : "Cliente editado con éxito.";
        clientService.save(client);
        sessionStatus.setComplete();
        redirectAttributes.addFlashAttribute("success", messageFlash);
        return "redirect:/list";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable(value = "id") Long id, RedirectAttributes redirectAttributes) {
        if (id > 0) {
            clientService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Cliente eliminado con éxito");
        }
        return "redirect:/list";
    }

}