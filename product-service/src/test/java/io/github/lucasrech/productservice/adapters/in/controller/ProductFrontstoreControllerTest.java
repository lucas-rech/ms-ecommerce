package io.github.lucasrech.productservice.adapters.in.controller;

import io.github.lucasrech.productservice.application.usecases.ProductUseCase;
import io.github.lucasrech.productservice.domain.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductFrontstoreControllerTest {

    @Mock
    private ProductUseCase productUseCase;

    @InjectMocks
    private ProductFrontstoreController productFrontstoreController;

    @Test
    @DisplayName("Should return all active products")
    void findAllProducts_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Product p = new Product(null, null, null, null, null, null, null, null, null, null, null, true);
        Page<Product> page = new PageImpl<>(List.of(p));

        when(productUseCase.findAll(pageable, true)).thenReturn(page);

        ResponseEntity<Page<Product>> response = productFrontstoreController.findAllProducts(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
        verify(productUseCase).findAll(pageable, true);
    }

    @Test
    @DisplayName("Should return active product by Id")
    void findProductById_ActiveProduct_Success() {
        Long id = 1L;
        Product p = new Product(null, null, null, null, null, null, null, null, null, null, null, true);

        when(productUseCase.findById(id)).thenReturn(p);

        ResponseEntity<Product> response = productFrontstoreController.findProductById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(p, response.getBody());
        verify(productUseCase).findById(id);
    }

    @Test
    @DisplayName("Should return 404 when product is inactive")
    void findProductById_InactiveProduct_ReturnsNotFound() {
        Long id = 1L;
        Product p = new Product(null, null, null, null, null, null, null, null, null, null, null, false);

        when(productUseCase.findById(id)).thenReturn(p);

        ResponseEntity<Product> response = productFrontstoreController.findProductById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productUseCase).findById(id);
    }
}
