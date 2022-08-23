package com.example.product.controller;

import com.example.product.entity.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {


//    private MockMvc mockMvc;
//
//    @Mock
//    com.example.product.service.ProductService productService;
//
//
//
//    @InjectMocks
//    ProductController testProductController;
//
//    ObjectMapper objectMapper=new ObjectMapper();
//
//    @BeforeEach
//    void setUp()
//    {
//        this.mockMvc= MockMvcBuilders.standaloneSetup(testProductController).build();
//    }
//
//
//
//
//    @Test
//    void getAllProducts() throws Exception {
//        List<Product> testProducts;
//        testProducts=new ArrayList<Product>();
//        testProducts.add(new Product(1,"Product 1","Product1 Description",100L,1,true,false));
//        testProducts.add(new Product(2,"Product 2","Product2 Description",200L,1,true,false));
//
//        when(this.productService.getAllProducts()).thenReturn(testProducts);
//
//        this.mockMvc.perform(get("/products"))
//                .andExpect(status().isOk())
//                .andDo(print());
//
//    }
//
//    @Test
//    void getProductById() throws Exception {
//
//        Integer productId=1;
//        Product testProduct=new Product(1,"Product 1","Product1 Description",100L,1,true,false);
//        when(this.productService.getProductById(productId)).thenReturn(testProduct);
//
//        this.mockMvc.perform(get("/products/{productId}",productId))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath(".productId").value(1))
//                .andDo(print());
//
//
//    }
//
//    @Test
//    void addProduct() throws Exception {
//
////        Integer productId=1;
////        Product testProduct=new Product(1,"Product 1","Product1 Description",100L,1,true,false);
////        when(this.productService.addOrUpdateProduct(testProduct)).thenReturn(testProduct);
////
////        ResultActions response = mockMvc.perform(post("/products")
////                        .content(objectMapper.writeValueAsString(testProduct))
////                        .contentType(MediaType.APPLICATION_JSON)
////                );
////
////        // then - verify the result or output using assert statements
////        response.andExpect(status().isCreated())
////                .andDo(print());
//
//
//    }
//
//    @Test
//    void updateProduct() throws Exception {
//
////        Integer productId=1;
////        Product testProduct=new Product(1,"Product 1","Product1 Description",100L,1,true,false);
////
////        when(this.productService.updateProduct(productId,testProduct)).thenReturn(testProduct);
////
////        this.mockMvc.perform(put("/products/{productId}",productId)
////                .content(objectMapper.writeValueAsString(testProduct))
////                .contentType(MediaType.APPLICATION_JSON))
////                .andExpect(status().isOk());
//    }
//
//    @Test
//    void deleteProduct() throws Exception {
//
//        Integer productId=1;
//        Product testProduct=new Product(1,"Product 1","Product1 Description",100L,1,true,false);
//
//        when(this.productService.getProductById(productId)).thenReturn(testProduct);
//
//        this.mockMvc.perform(delete("/products/{productId}",productId))
//                .andExpect(status().isOk());
//    }
}