package com.jonatnie.facturacionapp.model.dao;

import com.jonatnie.facturacionapp.model.entity.Invoice;

import org.springframework.data.repository.CrudRepository;

/**
 * IInvoiceDao
 */
public interface IInvoiceDao extends CrudRepository<Invoice, Long>{

    
}