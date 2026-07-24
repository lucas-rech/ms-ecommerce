package io.github.lucasrech.productservice.adapters.in.controller;

import io.github.lucasrech.productservice.application.usecases.ProductUseCase;
import io.github.lucasrech.productservice.domain.product.Product;
import io.github.lucasrech.productservice.domain.product.ProductRequestDTO;
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

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductUseCase productUseCase;

    @InjectMocks
    private ProductController productController;

    @Test
    @DisplayName("Should create product")
    void createProduct_Success() {
        ProductRequestDTO dto = new ProductRequestDTO(
                "Phone", "Desc", "123", "SKU", BigDecimal.TEN, (short) 1, List.of(1), true
        );

        doNothing().when(productUseCase).insert(any(ProductRequestDTO.class));

        ResponseEntity<Void> response = productController.createProduct(dto);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productUseCase).insert(dto);
    }

    @Test
    @DisplayName("Should update product")
    void updateProduct_Success() {
        Long id = 1L;
        ProductRequestDTO dto = new ProductRequestDTO(
                "Phone", "Desc", "123", "SKU", BigDecimal.TEN, (short) 1, List.of(1), true
        );

        doNothing().when(productUseCase).update(id, dto);

        ResponseEntity<Void> response = productController.updateProduct(id, dto);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productUseCase).update(id, dto);
    }

    @Test
    @DisplayName("Should delete product")
    void deleteProduct_Success() {
        Long id = 1L;

        doNothing().when(productUseCase).delete(id);

        ResponseEntity<Void> response = productController.deleteProductById(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productUseCase).delete(id);
    }

    @Test
    @DisplayName("Should return all products")
    void findAllProducts_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Product p = new Product(null, null, null, null, null, null, null, null, null, null, null, true);
        Page<Product> page = new PageImpl<>(List.of(p));

        when(productUseCase.findAll(pageable, true)).thenReturn(page);

        ResponseEntity<Page<Product>> response = productController.findAllProducts(true, pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
        verify(productUseCase).findAll(pageable, true);
    }

    @Test
    @DisplayName("Should return product by Id")
    void findProductById_Success() {
        Long id = 1L;
        Product p = new Product(null, null, null, null, null, null, null, null, null, null, null, true);

        when(productUseCase.findById(id)).thenReturn(p);

        ResponseEntity<Product> response = productController.findProductById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(p, response.getBody());
        verify(productUseCase).findById(id);
    }
}
