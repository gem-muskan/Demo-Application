package com.example.product.service;

import com.example.product.entity.Category;
import com.example.product.entity.Product;
import com.example.product.exception.ResourceNotFoundException;
import com.example.product.repository.CategoryRepository;
import com.example.product.repository.ProductRepository;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.migrationsupport.rules.EnableRuleMigrationSupport;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@EnableRuleMigrationSupport
class ProductServiceTest {

    @Mock
    ProductRepository testProductRepository;

    @Mock
    CategoryRepository testCategoryRepository;

    @InjectMocks
    ProductService testProductService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    List<Product> testProductList = new ArrayList<>();

    @Test
    void getAllProducts() {

        Product prod1 = new Product();
        prod1.setProductName("some-name");
        prod1.setProductPrice(12L);

        Product prod2 = new Product();
        prod2.setProductName("some-name-2");
        prod2.setProductPrice(12L);

        testProductList.addAll(Arrays.asList(prod1, prod2));

        when(testProductRepository.findAll()).thenReturn(testProductList);

        assertEquals(2, this.testProductService.getAllProducts().size());
    }

    @Test
    void getProductById() {

        Integer productId = 1;
        Product prod1 = new Product();
        prod1.setProductId(productId);
        prod1.setProductName("some-name");
        prod1.setProductDescription("some-desc");
        prod1.setProductPrice(12L);

        when(testProductRepository.findById(1)).thenReturn(Optional.of(prod1));

        assertEquals(1, this.testProductService.getProductById(productId).getProductId());
    }

    @Test
    void getProductByIdWhenIdDoesNotExistThrowsException() {
        Integer productId = 1;

        when(testProductRepository.findById(1)).thenReturn(Optional.empty());

        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("Product " + productId + " does not exist");

        testProductService.getProductById(productId);

    }

    @Test
    void getAllProductsForACategory() {
        Integer categoryId = 1;

        Category category1 = new Category();
        category1.setCategoryId(1);

        Category category2 = new Category();
        category2.setCategoryId(2);

        Product prod1 = new Product();
        prod1.setProductName("some-name");
        prod1.setProductPrice(12L);
        prod1.setCategory(category1);

        Product prod2 = new Product();
        prod2.setProductName("some-name-2");
        prod2.setProductPrice(12L);
        prod2.setCategory(category2);

        testProductList.addAll(Arrays.asList(prod1, prod2));

        when(testCategoryRepository.existsById(categoryId)).thenReturn(true);
        when(testProductRepository.findAll()).thenReturn(testProductList);

        assertEquals(1, testProductService.getAllProductsForACategory(categoryId).size());
    }

    @Test
    void getAllProductsForACategoryWhenCategoryDoesNotExist() {

        Integer categoryId = 1;

        when(testCategoryRepository.existsById(1)).thenReturn(false);

        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("Category " + categoryId + " does not exist");

        testProductService.getAllProductsForACategory(categoryId);

    }

    @Test
    void getProductForCategory() {

        Integer categoryId = 1;
        Category category1 = new Category();
        category1.setCategoryId(categoryId);


        Integer productId = 1;
        Product prod1 = new Product();
        prod1.setProductId(productId);
        prod1.setProductName("some-name");
        prod1.setProductDescription("some-desc");
        prod1.setProductPrice(12L);
        prod1.setCategory(category1);

        when(testCategoryRepository.existsById(1)).thenReturn(true);
        when(testProductRepository.findById(1)).thenReturn(Optional.of(prod1));

        assertEquals("some-name", testProductService.getProductForCategory(categoryId, productId).getProductName());

    }

    @Test
    void getProductForCategoryWhenCategoryDoesNotExistThrowsException() {
        Integer categoryId = 1;
        Integer productId = 12;

        when(testCategoryRepository.existsById(1)).thenReturn(false);

        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("Category" + categoryId + " does not exist");

        testProductService.getProductForCategory(categoryId, productId);
    }

    @Test
    void getProductForCategoryWhenProductDoesNotExistThrowsException() {
        Integer categoryId = 1;
        Integer productId = 12;

        when(testCategoryRepository.existsById(categoryId)).thenReturn(true);
        when(testProductRepository.findById(productId)).thenReturn(Optional.empty());

        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("Product " + productId + " does not exist");

        testProductService.getProductForCategory(categoryId, productId);
    }

    @Test
    void getProductForCategoryWhenProductDoesNotExistInCategoryThrowsException() {

        Integer categoryId = 1;
        Integer productId = 12;

        Category category1 = new Category();
        category1.setCategoryId(2);

        Product prod1 = new Product();
        prod1.setProductId(productId);
        prod1.setProductName("some-name");
        prod1.setProductDescription("some-desc");
        prod1.setProductPrice(12L);
        prod1.setCategory(category1);

        when(testCategoryRepository.existsById(categoryId)).thenReturn(true);
        when(testProductRepository.findById(productId)).thenReturn(Optional.of(prod1));

        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("Product " + productId + " does not exist for category");

        testProductService.getProductForCategory(categoryId, productId);

    }

    @Test
    void addProductToCategory() {

        Integer categoryId = 1;
        Integer productId = 1;

        Product prod1 = new Product();
        prod1.setProductId(productId);
        prod1.setProductName("some-name");
        prod1.setProductDescription("some-desc");
        prod1.setProductPrice(12L);

        Category category = new Category();
        category.setCategoryId(categoryId);
        prod1.setCategory(category);

        when(testCategoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(testProductRepository.save(prod1)).thenReturn(prod1);

        assertEquals(categoryId, testProductService.addProductToCategory(categoryId, prod1).getCategory().getCategoryId());

    }

    @Test
    void addProductToCategoryWhenCategoryDoesNotExistThenThrowException() {
        Integer categoryId = 1;
        Integer productId = 12;

        Product prod1 = new Product();
        prod1.setProductId(productId);

        when(testCategoryRepository.findById(1)).thenReturn(Optional.empty());

        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("Category" + categoryId + " does not exist");

        testProductService.addProductToCategory(categoryId, prod1);
    }

    @Test
    void updateProduct() {
        Integer productId = 1;

        Product prod1 = new Product();
        prod1.setProductId(productId);
        prod1.setProductName("some-name");
        prod1.setProductDescription("some-desc");
        prod1.setProductPrice(12L);


        Product updatedProduct = new Product();
        updatedProduct.setProductName("some-name");
        updatedProduct.setProductDescription("some-desc");
        updatedProduct.setProductPrice(25L);

        when(testProductRepository.findById(productId)).thenReturn(Optional.of(prod1));
        when(testProductRepository.save(prod1)).thenReturn(prod1);

        assertEquals(updatedProduct.getProductPrice(), testProductService.updateProduct(productId, updatedProduct).getProductPrice());
    }

    @Test
    void updateProductWhenProductDoesNotExistThenThrowException() {

        Integer productId = 1;
        Product updatedProduct = new Product();

        when(testProductRepository.findById(productId)).thenReturn(Optional.empty());

        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("Product " + productId + "does not exist");

        testProductService.updateProduct(productId, updatedProduct);

    }

    @Test
    void updateProductCategory() {

        Integer categoryId = 1;
        Integer categoryId2 = 2;
        Integer productId = 4;

        Product prod1 = new Product();
        prod1.setProductId(productId);
        prod1.setProductName("some-name");
        prod1.setProductDescription("some-desc");
        prod1.setProductPrice(12L);

        Category category = new Category();
        category.setCategoryId(categoryId);
        prod1.setCategory(category);

        Category category2 = new Category();
        category2.setCategoryId(categoryId2);
        prod1.setCategory(category2);

        when(testProductRepository.findById(productId)).thenReturn(Optional.of(prod1));
        when(testCategoryRepository.findById(categoryId2)).thenReturn(Optional.of(category2));
        when(testProductRepository.save(prod1)).thenReturn(prod1);

        assertEquals(categoryId2, testProductService.updateProductCategory(categoryId2, productId).getCategory().getCategoryId());


    }

    @Test
    void updateProductCategoryWhenProductDoesNotExistThrowsException() {
        Integer productId = 1;
        Integer categoryId = 2;

        when(testProductRepository.findById(productId)).thenReturn(Optional.empty());

        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("Product " + productId + "does not exist");

        testProductService.updateProductCategory(categoryId, productId);
    }

    @Test
    void updateProductCategoryWhenCategoryDoesNotExistThrowsException() {
        Integer productId = 1;
        Integer categoryId = 2;

        Product prod1 = new Product();

        when(testProductRepository.findById(productId)).thenReturn(Optional.of(prod1));

        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("Category " + categoryId + "does not exist");

        testProductService.updateProductCategory(categoryId, productId);
    }


    @Test
    void deleteProduct() {
        Integer productId = 1;

        doNothing().when(testProductRepository).deleteById(productId);
        testProductService.deleteProduct(productId);
        verify(testProductRepository, times(1)).deleteById(productId);
    }

    @Test
    void deleteAllProductsForACategory() {

        Integer categoryId = 1;
        Integer categoryId2 = 2;

        Category category1 = new Category();
        category1.setCategoryId(categoryId);

        Category category2 = new Category();
        category2.setCategoryId(categoryId2);

        Product prod1 = new Product();
        prod1.setProductName("some-name");
        prod1.setProductPrice(12L);
        prod1.setCategory(category1);

        Product prod2 = new Product();
        prod2.setProductName("some-name-2");
        prod2.setProductPrice(12L);
        prod2.setCategory(category2);

        testProductList.addAll(Arrays.asList(prod1, prod2));

        when(testCategoryRepository.existsById(categoryId2)).thenReturn(true);
        when(testProductRepository.findAll()).thenReturn(testProductList);

        testProductService.deleteAllProductsForACategory(categoryId2);

        verify(testProductRepository, never()).delete(prod1);
        verify(testProductRepository, times(1)).delete(prod2);


    }

    @Test
    void deleteAllProductsForACategoryWhenCategoryDoesNotExistThrowException() {

        Integer categoryId=1;

        when(testCategoryRepository.existsById(1)).thenReturn(false);

        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("Category " + categoryId + " does not exist");

        testProductService.deleteAllProductsForACategory(categoryId);

    }
}