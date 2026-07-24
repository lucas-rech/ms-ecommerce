package io.github.lucasrech.productservice.application.services;

import io.github.lucasrech.productservice.domain.category.Category;
import io.github.lucasrech.productservice.domain.category.CategoryRepository;
import io.github.lucasrech.productservice.domain.manufacturer.Manufacturer;
import io.github.lucasrech.productservice.domain.manufacturer.ManufacturerRepository;
import io.github.lucasrech.productservice.domain.product.Product;
import io.github.lucasrech.productservice.domain.product.ProductRepository;
import io.github.lucasrech.productservice.domain.product.ProductRequestDTO;
import io.github.lucasrech.productservice.utils.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Should insert a valid product")
    void insert_Success() {
        ProductRequestDTO dto = new ProductRequestDTO(
                "Phone", "Desc", "12345", "SKU123", BigDecimal.TEN, (short) 1, List.of(1), true
        );
        
        Manufacturer m = new Manufacturer((short)1, "M", "T", "C", LocalDateTime.now(), LocalDateTime.now());
        m.setActive(true);
        Category c = new Category(1, null, "C", LocalDateTime.now(), LocalDateTime.now(), true);

        when(productRepository.existsByEanCode("12345")).thenReturn(false);
        when(productRepository.existsBySkuCode("SKU123")).thenReturn(false);
        when(manufacturerRepository.findById((short) 1)).thenReturn(Optional.of(m));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(c));
        when(productRepository.save(any(Product.class))).thenReturn(new Product(null, null, null, null, null, null, null, null, null, null, null, true));

        productService.insert(dto);

        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("Should throw BusinessException when EAN exists")
    void insert_DuplicateEan_ThrowsException() {
        ProductRequestDTO dto = new ProductRequestDTO(
                "Phone", "Desc", "12345", "SKU123", BigDecimal.TEN, (short) 1, List.of(1), true
        );
        
        when(productRepository.existsByEanCode("12345")).thenReturn(true);

        assertThrows(BusinessException.class, () -> productService.insert(dto));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Should update product successfully")
    void update_Success() {
        Long id = 1L;
        ProductRequestDTO dto = new ProductRequestDTO(
                "Phone", "Desc", "12345", "SKU123", BigDecimal.TEN, (short) 1, List.of(1), true
        );
        
        Product existingProduct = new Product(1L, "SKUOLD", "EANOLD", "OldName", "OldDesc", null, BigDecimal.ONE, null, null, null, null, true);
        
        Manufacturer m = new Manufacturer((short)1, "M", "T", "C", LocalDateTime.now(), LocalDateTime.now());
        m.setActive(true);
        Category c = new Category(1, null, "C", LocalDateTime.now(), LocalDateTime.now(), true);

        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(productRepository.existsByEanCode("12345")).thenReturn(false);
        when(productRepository.existsBySkuCode("SKU123")).thenReturn(false);
        when(manufacturerRepository.findById((short) 1)).thenReturn(Optional.of(m));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(c));

        productService.update(id, dto);

        verify(productRepository).save(existingProduct);
        assertEquals("Phone", existingProduct.getName());
    }

    @Test
    @DisplayName("Should perform soft delete on product")
    void delete_Success() {
        Long id = 1L;
        Product existingProduct = new Product(1L, "SKU", "EAN", "Name", "Desc", null, BigDecimal.ONE, null, null, null, null, true);
        
        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));

        productService.delete(id);

        assertFalse(existingProduct.isActive());
        verify(productRepository).save(existingProduct);
    }
}
