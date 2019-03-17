package com.jonatnie.facturacionapp.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jonatnie.facturacionapp.model.entity.Client;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClientDaoImpl
 */
@Repository
public class ClientDaoImpl implements IClientDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        List<Client> clientList = entityManager.createQuery("FROM Client", Client.class).getResultList();
        return clientList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Client findOne(Long id) {
        Client client =  entityManager.find(Client.class, id);
        return client;
    }

    @Override
    @Transactional
    public void save(Client client) {
        if (client.getId() != null && client.getId() > 0) {
            entityManager.merge(client);
        }else{
            entityManager.persist(client);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Client client = this.findOne(id);
        entityManager.remove(client);
    }
}