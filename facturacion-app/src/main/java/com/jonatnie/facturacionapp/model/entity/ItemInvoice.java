package com.jonatnie.facturacionapp.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ItemInvoice
 */
@Entity
@Table(name = "items_invoice")
public class ItemInvoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }
    
    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double calculateAmount() {
        return this.quantity.doubleValue() * product.getCost();
    }

    
    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }
    
    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }
    
    private static final long serialVersionUID = 1L;
}