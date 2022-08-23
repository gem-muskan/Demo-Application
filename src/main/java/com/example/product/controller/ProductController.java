package com.example.product.controller;

import com.example.product.entity.Category;
import com.example.product.entity.Product;
import com.example.product.service.ProductService;
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
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllProducts()
    {
        List<Product> products=productService.getAllProducts();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId)
    {
        Product product=productService.getProductById(productId);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/products")
    public ResponseEntity<List<Product>> getAllProductsOfACategory(@PathVariable Integer categoryId)
    {
       List<Product> products=productService.getAllProductsForACategory(categoryId);
       return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/products/{productId}")
    public ResponseEntity<Product> getProductForACategory(@PathVariable Integer categoryId,@PathVariable Integer productId)
    {
        Product product=productService.getProductForCategory(categoryId,productId);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PostMapping("/category/{categoryId}/products")
    public ResponseEntity<Product> addProduct(@PathVariable Integer categoryId,@RequestBody Product addProduct)
    {
        Product product=productService.addProductToCategory(categoryId,addProduct);
        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,@RequestBody Product updatedProduct)
    {
        Product product=productService.updateProduct(productId,updatedProduct);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PutMapping("category/{categoryId}/products/{productId}")
    public ResponseEntity<HttpStatus> updateProductCategory(@PathVariable Integer categoryId,@PathVariable Integer productId){
        Product product=productService.updateProductCategory(categoryId,productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Integer productId)
    {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/category/{categoryId}/products")
    public ResponseEntity<HttpStatus> deleteAllProductsForACategory(@PathVariable Integer categoryId)
    {
        productService.deleteAllProductsForACategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
