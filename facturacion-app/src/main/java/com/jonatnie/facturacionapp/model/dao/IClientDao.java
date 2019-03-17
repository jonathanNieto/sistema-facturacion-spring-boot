package com.jonatnie.facturacionapp.model.dao;

import com.jonatnie.facturacionapp.model.entity.Client;

import org.springframework.data.repository.CrudRepository;

/**
 * IClientDao
 */
public interface IClientDao extends CrudRepository<Client, Long>{

}