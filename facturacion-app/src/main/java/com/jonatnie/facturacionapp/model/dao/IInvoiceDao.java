package com.jonatnie.facturacionapp.model.dao;

import com.jonatnie.facturacionapp.model.entity.Invoice;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * IInvoiceDao
 */
public interface IInvoiceDao extends CrudRepository<Invoice, Long>{

    @Query("SELECT inv FROM Invoice inv JOIN FETCH inv.client c JOIN FETCH inv.itemList itmInv JOIN FETCH itmInv.product WHERE inv.id = ?1")
    public Invoice fetchByIdWithClientWithItemInvoiceWithProduct(Long id);
    
}