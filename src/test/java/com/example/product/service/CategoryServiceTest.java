package com.example.product.service;

import com.example.product.entity.Category;
import com.example.product.exception.ResourceNotFoundException;
import com.example.product.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.*;
import org.junit.jupiter.migrationsupport.rules.EnableRuleMigrationSupport;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@EnableRuleMigrationSupport
class CategoryServiceTest {

    @Mock
    CategoryRepository testCategoryRepository;

    @InjectMocks
    CategoryService testCategoryService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    List<Category> mockCategoryList=new ArrayList<>();

    @Test
    void getAllCategories() {

        Integer cat1Id=1;
        Category cat1=new Category();
        cat1.setCategoryName("some-name");
        cat1.setCategoryDescription("some-description");
        cat1.setActive(true);
        cat1.setDeleted(false);

        Integer cat2Id=2;
        Category cat2=new Category();
        cat2.setCategoryName("some-name");
        cat2.setCategoryDescription("some-description");
        cat2.setActive(true);
        cat2.setDeleted(false);

        mockCategoryList.addAll(Arrays.asList(cat1,cat2));

        when(testCategoryRepository.findAll()).thenReturn(mockCategoryList);

        assertEquals(2,this.testCategoryService.getAllCategories().size());


    }

    @Test
    void getCategoryById() {

        Integer cat1Id=1;
        Category cat1=new Category();
        cat1.setCategoryName("some-name");
        cat1.setCategoryDescription("some-description");
        cat1.setActive(true);
        cat1.setDeleted(false);

        when(testCategoryRepository.findById(1)).thenReturn(Optional.of(cat1));

        assertEquals("some-name",this.testCategoryService.getCategoryById(1).getCategoryName());
    }

    @Test
    void getCategoryIdThrowsException() {
        Integer categoryId=1;

        when(testCategoryRepository.findById(1)).thenReturn(Optional.empty());

        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage(" Category " + categoryId + "does not exist");

        testCategoryService.getCategoryById(categoryId);

    }

    @Test
    void addCategory() {

        Integer cat1Id=1;
        Category cat1=new Category();
        cat1.setCategoryName("some-name");
        cat1.setCategoryDescription("some-description");

        when(testCategoryRepository.save(cat1)).thenReturn(cat1);

        assertEquals(true,testCategoryService.addCategory(cat1).isActive());

    }

    @Test
    void updateCategory() {

        Integer cat1Id=1;
        Category cat1=new Category();
        cat1.setCategoryName("some-name");
        cat1.setCategoryDescription("some-description");
        cat1.setActive(true);

        Category updatedCategory=new Category();
        updatedCategory.setActive(false);

        when(testCategoryRepository.findById(cat1Id)).thenReturn(Optional.of(cat1));
        when(testCategoryRepository.save(cat1)).thenReturn(cat1);

        assertEquals(false,testCategoryService.updateCategory(cat1Id,updatedCategory).isActive());
    }

    @Test
    void updateCategoryWhenCategoryIdDoesNotExist() {
        Integer categoryId=1;
        Category updatedCategory=new Category();

        when(testCategoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("Category " + categoryId + " does not exist ");

        testCategoryService.updateCategory(categoryId,updatedCategory);



    }

    @Test
    void deleteCategory() {

        Integer categoryId=1;

        doNothing().when(testCategoryRepository).deleteById(categoryId);
        testCategoryService.deleteCategory(categoryId);
        verify(testCategoryRepository,times(1)).deleteById(categoryId);
    }

    @Test
    void deleteAllCategories() {

        doNothing().when(testCategoryRepository).deleteAll();
        testCategoryService.deleteAllCategories();
        verify(testCategoryRepository,times(1)).deleteAll();
    }
}