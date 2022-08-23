package com.example.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;


import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Product extends Auditing {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer productId;

    @Column
    private String productName;

    @Column
    private String productDescription;

    @Column
    private boolean isActive;

    @Column
    private boolean isDeleted;

    public Product()
    {}

    public Product( String productName, String productDescription, boolean isActive, boolean isDeleted, Long productPrice, Category category) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.productPrice = productPrice;
        this.category = category;
    }

    @Column
    private Long productPrice;

    //
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
