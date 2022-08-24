package com.example.product.controller;

import com.example.product.entity.Category;
import com.example.product.entity.Product;
import com.example.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Slf4j
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/product")
    public ResponseEntity<?> getAllProducts()
    {
        try {
            log.info("Getting List of all Products");
            List<Product> products = productService.getAllProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
        catch(Exception e)
        {
            log.error("Exception occur: {}",e.getMessage());
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId)
    {
        log.info("Getting Product "+productId);
        Product product=productService.getProductById(productId);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/products")
    public ResponseEntity<List<Product>> getAllProductsOfACategory(@PathVariable Integer categoryId)
    {

            log.info("Getting the List of Products for Category" + categoryId);
            List<Product> products = productService.getAllProductsForACategory(categoryId);
            return new ResponseEntity<>(products, HttpStatus.OK);



    }

    @GetMapping("/category/{categoryId}/products/{productId}")
    public ResponseEntity<Product> getProductForACategory(@PathVariable Integer categoryId,@PathVariable Integer productId)
    {
        log.info("Getting the Product " + productId + "for category" + categoryId);
        Product product=productService.getProductForCategory(categoryId,productId);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PostMapping("/category/{categoryId}/products")
    public ResponseEntity<Product> addProduct(@PathVariable Integer categoryId,@RequestBody Product addProduct)
    {
        log.info("Adding Product" + addProduct + "to Category" + categoryId);
        Product product=productService.addProductToCategory(categoryId,addProduct);
        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,@RequestBody Product updatedProduct)
    {
        log.info("Updating the Product " + productId);
        Product product=productService.updateProduct(productId,updatedProduct);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PutMapping("category/{categoryId}/products/{productId}")
    public ResponseEntity<HttpStatus> updateProductCategory(@PathVariable Integer categoryId,@PathVariable Integer productId){

        log.info("Updating category "+ categoryId + " for product " + productId);
        Product product=productService.updateProductCategory(categoryId,productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Integer productId)
    {
        log.info("Deleting Product" + productId );
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/category/{categoryId}/products")
    public ResponseEntity<HttpStatus> deleteAllProductsForACategory(@PathVariable Integer categoryId)
    {
        log.info("Deleting All Products for Category" + categoryId);
        productService.deleteAllProductsForACategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
