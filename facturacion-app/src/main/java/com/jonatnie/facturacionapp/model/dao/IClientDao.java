package com.jonatnie.facturacionapp.model.dao;

import com.jonatnie.facturacionapp.model.entity.Client;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * IClientDao
 */
public interface IClientDao extends CrudRepository<Client, Long>{

    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.invoiceList il WHERE c.id = ?1")
    public Client fetchByIdWithInvoices(Long id);

}