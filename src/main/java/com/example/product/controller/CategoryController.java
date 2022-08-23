package com.example.product.controller;

import com.example.product.entity.Category;
import com.example.product.entity.Product;
import com.example.product.service.CategoryService;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategories()
    {
        List<Category> categoryList=categoryService.getAllCategories();
        return new ResponseEntity<>(categoryList,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer categoryId)
    {
        Category category=categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @PostMapping("/category")
    public ResponseEntity<Category> addCategory(@RequestBody Category newCategory)
    {
        Category category=categoryService.addCategory(newCategory);
        return new ResponseEntity<>(category,HttpStatus.CREATED);
    }

    @PutMapping("/category/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer categoryId,@RequestBody Category updateCategory)
    {
        Category category=categoryService.updateCategory(categoryId,updateCategory);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Integer categoryId)
    {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/category")
    public ResponseEntity<HttpStatus> deleteAllCategories()
    {
        categoryService.deleteAllCategories();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
