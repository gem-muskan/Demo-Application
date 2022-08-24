package com.example.product.controller;

import com.example.product.entity.Product;
import com.example.product.exception.ResourceNotFoundException;
import com.example.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@RunWith(SpringRunner.class)

class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService testProductService;

    @InjectMocks
    private ProductController testProductController;

    @Autowired
    private ObjectMapper objectMapper;

    List<Product> mockProductList=new ArrayList<Product>();

    @Test
    void getAllProducts() throws Exception {

        Integer productId=1;

        Product prod1=new Product();
        prod1.setProductId(productId);

        mockProductList.addAll(Arrays.asList(prod1));

        when(testProductService.getAllProducts()).thenReturn(mockProductList);

        this.mockMvc.perform(get("/product"))
                .andExpect(status().isOk());


    }

    @Test
    void getProductById() throws Exception {

        Integer productId=1;

        Product mockProduct=new Product();
        mockProduct.setProductId(productId);
        mockProduct.setProductDescription("some-description");
        mockProduct.setProductPrice(12L);

        Mockito.when(testProductService.getProductById(Mockito.anyInt())).thenReturn(mockProduct);

        this.mockMvc.perform(get("/product/{productId}",productId))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getProductByIdWhenProductDoesNotExist() throws Exception {

        Integer productId=1;

        Mockito.when(testProductService.getProductById(Mockito.anyInt())).thenThrow(ResourceNotFoundException.class);

        this.mockMvc.perform(get("/product/{productId}",productId))
                .andExpect(status().isNotFound())
                .andDo(print());

    }


    @Test
    void getAllProductsOfACategory() throws Exception {

            Integer categoryId=12;

            Integer productId=1;
            Product prod1=new Product();
            prod1.setProductId(productId);

            mockProductList.addAll(Arrays.asList(prod1));

            Mockito.when(testProductService.getAllProductsForACategory(Mockito.anyInt())).thenReturn(mockProductList);

            this.mockMvc.perform(get("/category/{categoryId}/products",categoryId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(mockProductList.size()))
                    .andDo(print());
    }

    @Test
    void getAllProductsOfACategoryWhenCategoryDoesNotExist() throws Exception{

        Integer categoryId=8;

        Mockito.when(testProductService.getAllProductsForACategory(Mockito.anyInt())).thenThrow(ResourceNotFoundException.class);

        this.mockMvc.perform(get("/category/{categoryId}/products",categoryId))
                .andExpect(status().isNotFound())
                .andDo(print());

    }
    @Test
    void getProductForACategory() throws Exception {

        Integer categoryId=1;
        Integer productId=1;

        Product mockProduct=new Product();
        mockProduct.setProductId(productId);
        mockProduct.setProductDescription("some-description");
        mockProduct.setProductPrice(12L);

        Mockito.when(testProductService.getProductForCategory(Mockito.anyInt(),Mockito.anyInt())).thenReturn(mockProduct);

        this.mockMvc.perform(get("/category/{categoryId}/products/{productId}",categoryId,productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(mockProduct.getProductId()))
                .andExpect(jsonPath("$.productDescription").value(mockProduct.getProductDescription()))
                .andExpect(jsonPath("$.productPrice").value(mockProduct.getProductPrice()))
                .andDo(print());
    }

    @Test
    void getProductForACategoryWhenCategoryDoesNotExist() throws Exception {

        Integer categoryId=8;
        Integer productId=5;

        Mockito.when(testProductService.getProductForCategory(Mockito.anyInt(),Mockito.anyInt())).thenThrow(new ResourceNotFoundException("Category Id does not exist"));

        this.mockMvc.perform(get("/category/{categoryId}/products/{productId}",categoryId,productId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Category Id does not exist"))
                .andDo(print());
    }

    @Test
    void getProductForACategoryWhenCategoryExistButProductDoesNotExist() throws Exception {

        Integer categoryId=8;
        Integer productId=5;

        Mockito.when(testProductService.getProductForCategory(Mockito.anyInt(),Mockito.anyInt())).thenThrow(new ResourceNotFoundException("Product Id does not exist"));

        this.mockMvc.perform(get("/category/{categoryId}/products/{productId}",categoryId,productId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product Id does not exist"))
                .andDo(print());
    }
    @Test
    void addProduct() throws Exception {

        Integer categoryId=1;

        Integer productId=1;

        Product mockProduct=new Product();
        mockProduct.setProductId(productId);
        mockProduct.setProductDescription("some-description");
        mockProduct.setProductPrice(12L);

        Mockito.when(testProductService.addProductToCategory(Mockito.anyInt(),Mockito.any(Product.class))).thenReturn(mockProduct);

        this.mockMvc.perform(post("/category/{categoryId}/products",categoryId)
                          .content(objectMapper.writeValueAsString(mockProduct))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.productDescription").value(mockProduct.getProductDescription()))
                .andExpect(jsonPath("$.productPrice").value(mockProduct.getProductPrice()))
                .andDo(print());
    }

    @Test
    void updateProduct() throws Exception {

        Integer productId=1;

        Product mockProduct=new Product();
        mockProduct.setProductId(productId);
        mockProduct.setProductDescription("some-description");
        mockProduct.setProductPrice(12L);

        Mockito.when(testProductService.updateProduct(Mockito.anyInt(),Mockito.any(Product.class))).thenReturn(mockProduct);

        this.mockMvc.perform(put("/product/{productId}",productId)
                        .content(objectMapper.writeValueAsString(mockProduct))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.productDescription").value(mockProduct.getProductDescription()))
                .andExpect(jsonPath("$.productPrice").value(mockProduct.getProductPrice()))
                .andDo(print());

    }

    @Test
    void updateProductWhenProductDoesNotExist() throws Exception {


        Integer productId=5;

        Product mockProduct=new Product();
        mockProduct.setProductId(productId);
        mockProduct.setProductDescription("some-description");
        mockProduct.setProductPrice(12L);

        Mockito.when(testProductService.updateProduct(Mockito.anyInt(),Mockito.any(Product.class))).thenThrow(new ResourceNotFoundException("Product Id does not exist"));

        this.mockMvc.perform(put("/product/{productId}",productId)
                        .content(objectMapper.writeValueAsString(mockProduct))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product Id does not exist"))
                .andDo(print());

    }

    @Test
    void updateProductCategory() throws Exception {

        Integer categoryId=1;
        Integer productId=2;

        Product mockProduct=new Product();
        mockProduct.setProductId(productId);
        mockProduct.setProductDescription("some-description");
        mockProduct.setProductPrice(12L);

        Mockito.when(testProductService.updateProductCategory(Mockito.anyInt(),Mockito.anyInt())).thenReturn(mockProduct);

        this.mockMvc.perform(put("/category/{categoryId}/products/{productId}",categoryId,productId)
                        .content(objectMapper.writeValueAsString(mockProduct))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    void updateProductCategoryWhenCategoryDoesNotExist() throws Exception {
        Integer productId=1;
        Integer categoryId=4;

        Mockito.when(testProductService.updateProductCategory(Mockito.anyInt(),Mockito.anyInt())).thenThrow(new ResourceNotFoundException("Category Id does not exist"));
        this.mockMvc.perform(put("/category/{categoryId}/products/{productId}",categoryId,productId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Category Id does not exist"))
                .andDo(print());
    }

    @Test
    void updateProductCategoryWhenProductDoesNotExist() throws Exception{

        Integer productId=1;
        Integer categoryId=4;

        Mockito.when(testProductService.updateProductCategory(Mockito.anyInt(),Mockito.anyInt())).thenThrow(new ResourceNotFoundException("Product Id does not exist"));


        this.mockMvc.perform(put("/category/{categoryId}/products/{productId}",categoryId,productId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product Id does not exist"))
                .andDo(print());
    }

    @Test
    void deleteProduct() throws Exception {

        Integer productId=1;
        doNothing().when(testProductService).deleteProduct(productId);

        this.mockMvc.perform(delete("/category/{categoryId}/products",productId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAllProductsForACategory() throws Exception {

        Integer categoryId=1;

        doNothing().when(this.testProductService).deleteAllProductsForACategory(Mockito.anyInt());

        this.mockMvc.perform(delete("/category/{categoryId}/products",categoryId))
                .andExpect(status().isNoContent());
    }
}