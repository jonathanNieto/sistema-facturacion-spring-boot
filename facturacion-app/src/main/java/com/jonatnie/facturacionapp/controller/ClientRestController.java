package com.jonatnie.facturacionapp.controller;

import java.util.List;

import com.jonatnie.facturacionapp.model.entity.Client;
import com.jonatnie.facturacionapp.model.service.IClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClientRestController
 */
@RestController
@RequestMapping("/api/client")
public class ClientRestController {

    @Autowired
    private IClientService clientService;

    /* api rest using @ResponseBody */
    @GetMapping(value = "/list")
    public List<Client> listRest() {
        return clientService.findAll();
    }
    
}