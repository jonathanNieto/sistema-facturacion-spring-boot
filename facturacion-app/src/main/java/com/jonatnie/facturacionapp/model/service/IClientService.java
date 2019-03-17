package com.jonatnie.facturacionapp.model.service;

import java.util.List;

import com.jonatnie.facturacionapp.model.entity.Client;

/**
 * IClientService
 */
public interface IClientService {

    public List<Client> findAll();

    public Client findOne(Long id);
    
    public void save(Client client);

    public void delete(Long id);
}