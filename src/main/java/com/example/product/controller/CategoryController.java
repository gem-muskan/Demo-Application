package com.example.product.controller;

import com.example.product.entity.Category;
import com.example.product.service.CategoryService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Controller Class*/
@RestController
@Slf4j
public class CategoryController {

    /**
     * Category Service*/
    @Autowired
    CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategories() {
        /**
         * List of Categories*/
        try {
            log.info("Getting List of all Categories");
            List<Category> categoryList = categoryService.getAllCategories();
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        }
        catch(Exception e) {
            log.error("Exception occur: {}", e.getMessage());
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer categoryId) {
        log.info("Getting the Category" + categoryId);
        Category category = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @PostMapping("/category")
    public ResponseEntity<Category> addCategory(@RequestBody Category newCategory) {
        try {
            log.info("Adding the category");
            Category category = categoryService.addCategory(newCategory);
            return ResponseEntity.status(HttpStatus.CREATED).body(category);
        }
        catch(Exception e) {
            log.error("Exception occur: {}", e.getMessage());
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/category/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer categoryId, @RequestBody Category updateCategory) {
        log.info("Updating the category" + categoryId);
        Category category=categoryService.updateCategory(categoryId, updateCategory);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Integer categoryId) {
        log.info("Deleting the category" + categoryId);
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/category")
    public ResponseEntity<HttpStatus> deleteAllCategories() {
        log.info("Deleting All Categories");
        categoryService.deleteAllCategories();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
