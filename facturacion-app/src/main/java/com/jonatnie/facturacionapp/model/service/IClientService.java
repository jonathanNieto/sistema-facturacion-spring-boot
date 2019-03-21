package com.jonatnie.facturacionapp.model.service;

import java.util.List;

import com.jonatnie.facturacionapp.model.entity.Client;
import com.jonatnie.facturacionapp.model.entity.Invoice;
import com.jonatnie.facturacionapp.model.entity.Product;

/**
 * IClientService
 */
public interface IClientService {

    public List<Client> findAll();

    public Client findOne(Long id);
    
    public void save(Client client);

    public void delete(Long id);

    public List<Product> findByName(String term);
    
    public void saveInvoice(Invoice invoice);

    public Product findProductById(Long id);

    public Invoice findInvoiceById(Long id);

    public void deleteInvoice(Long id);
}