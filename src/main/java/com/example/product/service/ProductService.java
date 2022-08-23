package com.example.product.service;

import com.example.product.entity.Category;
import com.example.product.entity.Product;
import com.example.product.exception.ResourceNotFoundException;
import com.example.product.repository.CategoryRepository;
import com.example.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product " + productId +" does not exist"));
    }
    public List<Product> getAllProductsForACategory(Integer categoryId)
    {
        List<Product> products=new ArrayList<>();
        if(!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category " + categoryId + " does not exist");
        }

        productRepository.findAll().forEach(product -> {
            if(product.getCategory().getCategoryId() == categoryId) {
              products.add(product);
            }
        });
                return products;
    }


    public Product getProductForCategory(Integer categoryId,Integer productId) {
        if(!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category" + categoryId + " does not exist");
        }

        Product product= productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product " + productId + " does not exist" ));

                if(product.getCategory().getCategoryId() == categoryId) {

                    return product;
                }
                else {
                  throw new ResourceNotFoundException("Product " + productId +" does not exist for category " + categoryId);
                }

    }

    public Product addProductToCategory(Integer categoryId,Product addProduct) {
        Product product=categoryRepository.findById(categoryId).map(category -> { addProduct.setCategory(category);
            addProduct.setCreatedAt(new Date());
            addProduct.setUpdatedAt(new Date());
         return productRepository.save(addProduct); })
                .orElseThrow(() -> new ResourceNotFoundException("Category" + categoryId + " does not exist"));

        return product;
    }

    public Product updateProduct(Integer productId,Product updatedProduct)
    {
        Product product=productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product " + productId +"does not exist"));
        product.setProductName(updatedProduct.getProductName());
        product.setProductDescription(updatedProduct.getProductDescription());
        product.setProductPrice(updatedProduct.getProductPrice());
        product.setActive(updatedProduct.isActive());
        product.setDeleted(updatedProduct.isDeleted());
        product.setUpdatedAt(new Date());

        return productRepository.save(product);
    }

    public Product updateProductCategory(Integer categoryId, Integer productId) {

//        if(!categoryRepository.existsById(categoryId)) {
//            throw new ResourceNotFoundException("Category " + categoryId +"does not  exist");
//        }
//
//        Category category=categoryRepository.findById(categoryId).get();
//        category.setUpdatedAt(new Date());

        Product product=productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product " + productId +"does not exist"));;
        product.setCategory(categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category " + categoryId +"does not exist")));
        product.setUpdatedAt(new Date());

        return productRepository.save(product);
    }

    public void deleteProduct(Integer productId)
    {
        productRepository.deleteById(productId);
    }

    public void deleteAllProductsForACategory(Integer categoryId) {
        if(!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category " + categoryId + " does not exist");
        }

        productRepository.findAll().forEach(product -> {
            if(product.getCategory().getCategoryId() == categoryId)
            {
               productRepository.delete(product);
            }
        });

    }


}