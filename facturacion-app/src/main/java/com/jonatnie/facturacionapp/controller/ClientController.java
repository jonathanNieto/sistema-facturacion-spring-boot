package com.jonatnie.facturacionapp.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.jonatnie.facturacionapp.model.dao.IClientDao;
import com.jonatnie.facturacionapp.model.entity.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * ClientController
 */
@Controller
@SessionAttributes("client")
public class ClientController {

    @Autowired
    private IClientDao clientDao;

    @RequestMapping(value="/list", method=RequestMethod.GET)
    public String list(Model model) {
        List<Client> clientList = clientDao.findAll();
        model.addAttribute("title", "Listado de clientes");
        model.addAttribute("clients", clientList);
        return "list";
    }

    @RequestMapping(value="/form", method=RequestMethod.GET)
    public String create(Map<String, Object> model) {
        Client client = new Client();
        model.put("client", client);
        model.put("title", "Formulario Cliente");
        return "form";
    }
    @RequestMapping(value="/form/{id}", method=RequestMethod.GET)
    public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model) {

        Client client = null;

        if (id > 0) {
            client = clientDao.findOne(id);
        }else{
            return "redirect:/list";
        }
        model.put("title", "Editando Cliente");
        model.put("client", client);
        return "form";
    }
    
    @RequestMapping(value="/form", method=RequestMethod.POST)
    public String save(@Valid Client client, BindingResult bindingResult, Model model, SessionStatus sessionStatus) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Formulario Cliente");
            return "form";
        }
        clientDao.save(client);
        sessionStatus.setComplete();
        return "redirect:/list";
    }
    
    
}