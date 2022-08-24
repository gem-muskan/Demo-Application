package com.example.product.entity;

import javax.persistence.*;

/**
 * Category*/
@Entity
@Table (name = "CATEGORY")
public class Category extends Auditing {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer categoryId;

    @Column
    private String categoryName;

    @Column
    private String categoryDescription;

    @Column
    private boolean isActive;

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

    @Column
    private boolean isDeleted;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    /** Constructor*/
    public Category() {

    }

    /**
     * Constructor of Category Class*/

    public Category(String categoryName, String categoryDescription, boolean isActive, boolean isDeleted) {
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
    }
}
