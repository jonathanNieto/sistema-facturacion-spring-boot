package com.jonatnie.facturacionapp.model.service;

import java.util.List;

import com.jonatnie.facturacionapp.model.dao.IClientDao;
import com.jonatnie.facturacionapp.model.dao.IInvoiceDao;
import com.jonatnie.facturacionapp.model.dao.IProductDao;
import com.jonatnie.facturacionapp.model.entity.Client;
import com.jonatnie.facturacionapp.model.entity.Invoice;
import com.jonatnie.facturacionapp.model.entity.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClientServiceImpl
 */
@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private IClientDao clientDao;

    @Autowired
    private IInvoiceDao invoiceDao;

    @Autowired
    private IProductDao productDao;

    /* client methods */
    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        List<Client> clienList = (List<Client>) clientDao.findAll();
        return clienList;
    }

    @Override
    @Transactional(readOnly = true)
    public Client findOne(Long id) {
        Client client = clientDao.findById(id).orElse(null);
        return client;
    }

    @Override
    @Transactional(readOnly = true)
    public Client fetchByIdWithInvoices(Long id) {
        return clientDao.fetchByIdWithInvoices(id);
    }

    @Override
    @Transactional
    public void save(Client client) {
        clientDao.save(client);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clientDao.deleteById(id);
    }

    /* Invoice  methods*/
    @Override
    @Transactional
    public void saveInvoice(Invoice invoice) {
        invoiceDao.save(invoice);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Invoice findInvoiceById(Long id) {
        return invoiceDao.findById(id).orElse(null);
    }
    
    @Override
    @Transactional
    public void deleteInvoice(Long id) {
        invoiceDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Invoice fetchByIdWithClientWithItemInvoiceWithProduct(Long id) {
        return invoiceDao.fetchByIdWithClientWithItemInvoiceWithProduct(id);
    }

    /* product methods */
    @Override
    @Transactional(readOnly = true)
    public List<Product> findByName(String term) {
        /* return productDao.findByName(term); */
        return productDao.findByNameLikeIgnoreCase( term + "%");
    }
    
    @Override
    @Transactional(readOnly = true)
    public Product findProductById(Long id) {
        return productDao.findById(id).orElse(null);
    }
    
}