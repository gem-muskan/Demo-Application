package com.example.product.service;

import com.example.product.controller.CategoryController;
import com.example.product.entity.Category;
import com.example.product.exception.ResourceNotFoundException;
import com.example.product.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepo;

    Logger log = LoggerFactory.getLogger(CategoryController.class);

    public List<Category> getAllCategories()
    {
        return categoryRepo.findAll();
    }
    public Category getCategoryById(Integer categoryId)
    {

         return categoryRepo.findById(categoryId)
         .orElseThrow(() -> new ResourceNotFoundException(" Category " + categoryId + "does not exist"));
    }
    public Category addCategory(Category newCategory)
    {

        newCategory.setCreatedAt(new Date());
        newCategory.setUpdatedAt(new Date());
        newCategory.setActive(true);
        newCategory.setDeleted(false);
        return categoryRepo.save(newCategory);
    }

    public Category updateCategory(Integer categoryId,Category updatedCategory){

        Category category=categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category " + categoryId + " does not exist "));
        category.setCategoryName(updatedCategory.getCategoryName());
        category.setCategoryDescription(updatedCategory.getCategoryDescription());
        category.setActive(updatedCategory.isActive());
        category.setDeleted(updatedCategory.isDeleted());
        category.setUpdatedAt(new Date());

        return categoryRepo.save(category);
    }
    public void deleteCategory(Integer categoryId) {
        categoryRepo.deleteById(categoryId);
    }

    public void deleteAllCategories() {
        categoryRepo.deleteAll();
    }
}
