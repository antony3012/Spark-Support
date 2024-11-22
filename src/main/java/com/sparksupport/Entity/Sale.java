package com.sparksupport.Entity;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(insertable = false, updatable = false)
    private long productId;
    private int quantity;
    private LocalDate saleDate;


    public Sale() {
    }

    public Sale(long id, long productId, int quantity, LocalDate saleDate) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.saleDate = saleDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

}

