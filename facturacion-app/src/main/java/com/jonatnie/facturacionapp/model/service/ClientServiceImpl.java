package com.jonatnie.facturacionapp.model.service;

import java.util.List;

import com.jonatnie.facturacionapp.model.dao.IClientDao;
import com.jonatnie.facturacionapp.model.entity.Client;

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

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        List<Client> clienList = (List<Client>) clientDao.findAll();
        return clienList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Client findOne(Long id) {
        Client client = clientDao.findOne(id);
        return client;
    }

    @Override
    @Transactional
    public void save(Client client) {
        clientDao.save(client);
    }


    @Override
    @Transactional
    public void delete(Long id) {
        clientDao.delete(id);
    }

    
}