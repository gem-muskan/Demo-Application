package com.example.product.controller;

import com.example.product.entity.Category;
import com.example.product.exception.ResourceNotFoundException;
import com.example.product.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@RunWith(SpringRunner.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService testCategoryService;

    @InjectMocks
    private CategoryController testCategoryController;

    @Autowired
    private ObjectMapper objectMapper;

    List<Category> mockCategoryList=new ArrayList<Category>();


    @Test
    void getAllCategories() throws Exception {


        Category cat1=new Category();
        cat1.setCategoryName("some-name");
        cat1.setCategoryDescription("some-description");
        cat1.setActive(true);
        cat1.setDeleted(false);

        mockCategoryList.addAll(Arrays.asList(cat1));
        when(this.testCategoryService.getAllCategories()).thenReturn(mockCategoryList);

        this.mockMvc.perform(get("/category"))
                .andExpect(status().isOk());
                //.andExpect(content().mockCategoryList);


    }

    @Test
    void getCategoryById() throws Exception {

        Integer categoryId=1;

        Category cat1=new Category();
        cat1.setCategoryId(categoryId);
        cat1.setCategoryName("some-name");
        cat1.setCategoryDescription("some-description");
        cat1.setActive(true);
        cat1.setDeleted(false);

        when(this.testCategoryService.getCategoryById(categoryId)).thenReturn(cat1);

        this.mockMvc.perform(get("/category/{categoryId}",categoryId))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void getCategoryByIdReturnNotFound() throws Exception
    {
        Integer categoryId=1;

        when(this.testCategoryService.getCategoryById(categoryId)).thenThrow( ResourceNotFoundException.class);

        this.mockMvc.perform(get("/category/{categoryId}",categoryId))
                .andExpect(status().isNotFound());


    }

    @Test
    void addCategory() throws Exception {

        Integer categoryId=1;

        Category cat1=new Category();
        cat1.setCategoryId(categoryId);
        cat1.setCategoryName("some-name");
        cat1.setCategoryDescription("some-description");
        cat1.setActive(true);
        cat1.setDeleted(false);
        cat1.setCreatedAt(new Date());
        cat1.setUpdatedAt(new Date());

        Mockito.when(testCategoryService.addCategory(Mockito.any(Category.class))).thenReturn(cat1);

        this.mockMvc.perform(
                post("/category")
                .content(objectMapper.writeValueAsString(cat1 ))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryId").value(1))
                .andExpect(jsonPath("$.categoryName").value("some-name"))
                .andExpect(jsonPath("$.categoryDescription").value("some-description"))
                .andDo(print());
    }

    @Test
    void updateCategory() throws Exception {

        Integer categoryId=1;

        Category testCategory=new Category();
        testCategory.setCategoryId(1);
        testCategory.setCategoryName("some-name");
        testCategory.setCategoryDescription("some-description");


        Mockito.when(this.testCategoryService.updateCategory(Mockito.any(Integer.class),Mockito.any(Category.class))).thenReturn(testCategory);

        this.mockMvc.perform(put("/category/{categoryId}",categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCategory)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.categoryId").value(1))
                        .andExpect(jsonPath("$.categoryName").value("some-name"))
                        .andExpect(jsonPath("$.categoryDescription").value("some-description"))
                        .andDo(print());
    }

    @Test
    void updateCategoryWhenCategoryDoesNotExistThenReturnNotFound() throws Exception {

        Integer categoryId=1;
        Category testCategory=new Category();
        testCategory.setCategoryId(1);
        testCategory.setCategoryName("some-name");
        testCategory.setCategoryDescription("some-description");

        Mockito.when(testCategoryService.updateCategory(Mockito.anyInt(),Mockito.any(Category.class))).thenThrow(ResourceNotFoundException.class);

        this.mockMvc.perform(put("/category/{categoryId}",categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCategory)))
                .andExpect(status().isNotFound());

    }


    @Test
    void deleteCategory() throws Exception {

        Integer categoryId=1;
        doNothing().when(this.testCategoryService).deleteCategory(categoryId);

        this.mockMvc.perform(delete("/category/{categoryId}",categoryId))
                   .andExpect(status().isNoContent());

    }

    @Test
    void deleteAllCategories() throws Exception {

        doNothing().when(this.testCategoryService).deleteAllCategories();

        this.mockMvc.perform(delete("/category"))
                .andExpect(status().isNoContent());
    }
}