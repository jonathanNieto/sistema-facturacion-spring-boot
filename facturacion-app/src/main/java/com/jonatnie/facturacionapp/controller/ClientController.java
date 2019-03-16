package com.jonatnie.facturacionapp.controller;

import java.util.List;

import com.jonatnie.facturacionapp.model.dao.IClientDao;
import com.jonatnie.facturacionapp.model.entity.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * ClientController
 */
@Controller
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
    
    
}