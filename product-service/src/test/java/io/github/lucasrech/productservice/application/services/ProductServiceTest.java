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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    @DisplayName("Should throw BusinessException when inserting null DTO")
    void insert_NullDTO_ThrowsException() {
        assertThrows(BusinessException.class, () -> productService.insert(null));
    }

    @Test
    @DisplayName("Should throw BusinessException when inserting and EAN exists")
    void insert_DuplicateEan_ThrowsException() {
        ProductRequestDTO dto = new ProductRequestDTO(
                "Phone", "Desc", "12345", "SKU123", BigDecimal.TEN, (short) 1, List.of(1), true
        );
        
        when(productRepository.existsByEanCode("12345")).thenReturn(true);

        assertThrows(BusinessException.class, () -> productService.insert(dto));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Should throw BusinessException when inserting and SKU exists")
    void insert_DuplicateSku_ThrowsException() {
        ProductRequestDTO dto = new ProductRequestDTO(
                "Phone", "Desc", "12345", "SKU123", BigDecimal.TEN, (short) 1, List.of(1), true
        );
        
        when(productRepository.existsByEanCode("12345")).thenReturn(false);
        when(productRepository.existsBySkuCode("SKU123")).thenReturn(true);

        assertThrows(BusinessException.class, () -> productService.insert(dto));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Should throw BusinessException when inserting with inactive manufacturer")
    void insert_InactiveManufacturer_ThrowsException() {
        ProductRequestDTO dto = new ProductRequestDTO(
                "Phone", "Desc", "12345", "SKU123", BigDecimal.TEN, (short) 1, List.of(1), true
        );
        Manufacturer m = new Manufacturer((short)1, "M", "T", "C", LocalDateTime.now(), LocalDateTime.now());
        m.setActive(false);
        
        when(productRepository.existsByEanCode("12345")).thenReturn(false);
        when(productRepository.existsBySkuCode("SKU123")).thenReturn(false);
        when(manufacturerRepository.findById((short) 1)).thenReturn(Optional.of(m));

        assertThrows(BusinessException.class, () -> productService.insert(dto));
    }

    @Test
    @DisplayName("Should throw BusinessException when inserting with missing manufacturer")
    void insert_MissingManufacturer_ThrowsException() {
        ProductRequestDTO dto = new ProductRequestDTO(
                "Phone", "Desc", "12345", "SKU123", BigDecimal.TEN, (short) 1, List.of(1), true
        );
        
        when(productRepository.existsByEanCode("12345")).thenReturn(false);
        when(productRepository.existsBySkuCode("SKU123")).thenReturn(false);
        when(manufacturerRepository.findById((short) 1)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> productService.insert(dto));
    }

    @Test
    @DisplayName("Should throw BusinessException when inserting with inactive category")
    void insert_InactiveCategory_ThrowsException() {
        ProductRequestDTO dto = new ProductRequestDTO(
                "Phone", "Desc", "12345", "SKU123", BigDecimal.TEN, (short) 1, List.of(1), true
        );
        Manufacturer m = new Manufacturer((short)1, "M", "T", "C", LocalDateTime.now(), LocalDateTime.now());
        m.setActive(true);
        Category c = new Category(1, null, "C", LocalDateTime.now(), LocalDateTime.now(), false);
        
        when(productRepository.existsByEanCode("12345")).thenReturn(false);
        when(productRepository.existsBySkuCode("SKU123")).thenReturn(false);
        when(manufacturerRepository.findById((short) 1)).thenReturn(Optional.of(m));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(c));

        assertThrows(BusinessException.class, () -> productService.insert(dto));
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
    @DisplayName("Should throw BusinessException when updating with duplicate EAN")
    void update_DuplicateEan_ThrowsException() {
        Long id = 1L;
        ProductRequestDTO dto = new ProductRequestDTO(
                "Phone", "Desc", "12345", "SKU123", BigDecimal.TEN, (short) 1, List.of(1), true
        );
        Product existingProduct = new Product(1L, "SKUOLD", "EANOLD", "OldName", "OldDesc", null, BigDecimal.ONE, null, null, null, null, true);
        
        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(productRepository.existsByEanCode("12345")).thenReturn(true);

        assertThrows(BusinessException.class, () -> productService.update(id, dto));
    }

    @Test
    @DisplayName("Should update product without changing codes when they remain the same")
    void update_SameCodes_Success() {
        Long id = 1L;
        ProductRequestDTO dto = new ProductRequestDTO(
                "Phone", "Desc", "EANOLD", "SKUOLD", BigDecimal.TEN, null, null, null
        );
        Product existingProduct = new Product(1L, "SKUOLD", "EANOLD", "OldName", "OldDesc", null, BigDecimal.ONE, null, null, null, null, true);
        
        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));

        productService.update(id, dto);

        verify(productRepository).save(existingProduct);
        assertEquals("Phone", existingProduct.getName());
    }

    @Test
    @DisplayName("Should throw BusinessException when updating null ID or DTO")
    void update_NullArguments_ThrowsException() {
        assertThrows(BusinessException.class, () -> productService.update(null, new ProductRequestDTO("1", "2", "3", "4", BigDecimal.ONE, null, null, true)));
        assertThrows(BusinessException.class, () -> productService.update(1L, null));
    }

    @Test
    @DisplayName("Should throw BusinessException when updating non-existent product")
    void update_NotFound_ThrowsException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> productService.update(1L, new ProductRequestDTO("1", "2", "3", "4", BigDecimal.ONE, null, null, true)));
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

    @Test
    @DisplayName("Should throw BusinessException when deleting null ID")
    void delete_NullId_ThrowsException() {
        assertThrows(BusinessException.class, () -> productService.delete(null));
    }

    @Test
    @DisplayName("Should throw BusinessException when deleting non-existent product")
    void delete_NotFound_ThrowsException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> productService.delete(1L));
    }

    @Test
    @DisplayName("Should find product by ID")
    void findById_Success() {
        Long id = 1L;
        Product existingProduct = new Product(1L, "SKU", "EAN", "Name", "Desc", null, BigDecimal.ONE, null, null, null, null, true);
        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));

        Product result = productService.findById(id);

        assertEquals(existingProduct, result);
    }

    @Test
    @DisplayName("Should throw BusinessException when finding by null ID")
    void findById_NullId_ThrowsException() {
        assertThrows(BusinessException.class, () -> productService.findById(null));
    }

    @Test
    @DisplayName("Should throw BusinessException when finding non-existent product")
    void findById_NotFound_ThrowsException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> productService.findById(1L));
    }

    @Test
    @DisplayName("Should find all products (active only)")
    void findAll_ActiveOnly() {
        Pageable pageable = PageRequest.of(0, 10);
        Product product = new Product(1L, "SKU", "EAN", "Name", "Desc", null, BigDecimal.ONE, null, null, null, null, true);
        Page<Product> page = new PageImpl<>(List.of(product));

        when(productRepository.findAllByIsActive(true, pageable)).thenReturn(page);

        Page<Product> result = productService.findAll(pageable, true);

        assertEquals(page, result);
    }

    @Test
    @DisplayName("Should find all products (all statuses)")
    void findAll_AllStatuses() {
        Pageable pageable = PageRequest.of(0, 10);
        Product product = new Product(1L, "SKU", "EAN", "Name", "Desc", null, BigDecimal.ONE, null, null, null, null, true);
        Page<Product> page = new PageImpl<>(List.of(product));

        when(productRepository.findAll(pageable)).thenReturn(page);

        Page<Product> result = productService.findAll(pageable, null);

        assertEquals(page, result);
    }

    @Test
    @DisplayName("Should find products by manufacturer")
    void findAllByManufacturer_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Manufacturer m = new Manufacturer((short)1, "M", "T", "C", LocalDateTime.now(), LocalDateTime.now());
        Product product = new Product(1L, "SKU", "EAN", "Name", "Desc", null, BigDecimal.ONE, null, null, null, null, true);
        Page<Product> page = new PageImpl<>(List.of(product));

        when(manufacturerRepository.findById((short)1)).thenReturn(Optional.of(m));
        when(productRepository.findAllByManufacturer(m, pageable)).thenReturn(page);

        Page<Product> result = productService.findAllByManufacturer((short)1, pageable);

        assertEquals(page, result);
    }

    @Test
    @DisplayName("Should throw BusinessException when finding products by non-existent manufacturer")
    void findAllByManufacturer_NotFound_ThrowsException() {
        Pageable pageable = PageRequest.of(0, 10);
        when(manufacturerRepository.findById((short)1)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> productService.findAllByManufacturer((short)1, pageable));
    }
}
