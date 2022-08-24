package com.example.product.repository;

import com.example.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Product Repository*/
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

//    List<Product> findByCategoryId(Integer categoryId) ;
//    void deleteByCategoryId(Integer categoryId);
}
