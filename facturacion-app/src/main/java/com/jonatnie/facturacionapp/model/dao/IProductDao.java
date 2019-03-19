package com.jonatnie.facturacionapp.model.dao;

import java.util.List;

import com.jonatnie.facturacionapp.model.entity.Product;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * IProductDao
 */
public interface IProductDao extends CrudRepository<Product, Long>{

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    public List<Product> findByName(String name);

    public List<Product> findByNameLikeIgnoreCase(String term);
}