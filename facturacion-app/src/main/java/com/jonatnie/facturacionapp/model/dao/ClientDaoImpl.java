package com.jonatnie.facturacionapp.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jonatnie.facturacionapp.model.entity.Client;

import org.springframework.stereotype.Repository;

/**
 * ClientDaoImpl
 */
@Repository
public class ClientDaoImpl implements IClientDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Client> findAll() {
        List<Client> clientList = entityManager.createQuery("FROM Client", Client.class).getResultList();
        return clientList;
    }
    
    @Override
    public Client findOne(Long id) {
        Client client =  entityManager.find(Client.class, id);
        return client;
    }

    @Override
    public void save(Client client) {
        if (client.getId() != null && client.getId() > 0) {
            entityManager.merge(client);
        }else{
            entityManager.persist(client);
        }
    }

    @Override
    public void delete(Long id) {
        Client client = this.findOne(id);
        entityManager.remove(client);
    }
}