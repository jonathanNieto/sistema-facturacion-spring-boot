package com.jonatnie.facturacionapp.model.dao;

import java.util.List;

import com.jonatnie.facturacionapp.model.entity.Client;

/**
 * IClientDao
 */
public interface IClientDao {

    public List<Client> findAll(); 
}