package io.github.lucasrech.productservice.adapters.out.persistence;

import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaManufacturerEntity;
import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaProductEntity;
import io.github.lucasrech.productservice.adapters.out.persistence.repositories.JpaProductRepository;
import io.github.lucasrech.productservice.domain.manufacturer.Manufacturer;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryAdapterTest {

    @Mock
    private JpaProductRepository jpaProductRepository;

    @InjectMocks
    private ProductRepositoryAdapter productRepositoryAdapter;

    @Test
    @DisplayName("Should save product")
    void save_Success() {
        Product domain = new Product(1L, "SKU", "EAN", "Name", "Desc", null, BigDecimal.TEN, null, null, null, null, true);
        
        JpaProductEntity entity = new JpaProductEntity();
        entity.setId(1L);

        when(jpaProductRepository.save(any(JpaProductEntity.class))).thenReturn(entity);

        Product result = productRepositoryAdapter.save(domain);

        assertEquals(1L, result.getId());
        verify(jpaProductRepository).save(any(JpaProductEntity.class));
    }

    @Test
    @DisplayName("Should find product by id")
    void findById_Success() {
        JpaProductEntity entity = new JpaProductEntity();
        entity.setId(1L);

        when(jpaProductRepository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Product> result = productRepositoryAdapter.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(jpaProductRepository).findById(1L);
    }

    @Test
    @DisplayName("Should find all products")
    void findAll_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        JpaProductEntity entity = new JpaProductEntity();
        entity.setId(1L);
        Page<JpaProductEntity> page = new PageImpl<>(List.of(entity));

        when(jpaProductRepository.findAll(pageable)).thenReturn(page);

        Page<Product> result = productRepositoryAdapter.findAll(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(1L, result.getContent().get(0).getId());
        verify(jpaProductRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Should find active products")
    void findAllByIsActive_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        JpaProductEntity entity = new JpaProductEntity();
        entity.setId(1L);
        Page<JpaProductEntity> page = new PageImpl<>(List.of(entity));

        when(jpaProductRepository.findAllByIsActive(eq(true), eq(pageable))).thenReturn(page);

        Page<Product> result = productRepositoryAdapter.findAllByIsActive(true, pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(1L, result.getContent().get(0).getId());
        verify(jpaProductRepository).findAllByIsActive(eq(true), eq(pageable));
    }

    @Test
    @DisplayName("Should check if exists by EAN")
    void existsByEan_Success() {
        when(jpaProductRepository.existsByCdEan("123")).thenReturn(true);
        boolean exists = productRepositoryAdapter.existsByEanCode("123");
        assertTrue(exists);
        verify(jpaProductRepository).existsByCdEan("123");
    }

    @Test
    @DisplayName("Should check if exists by SKU")
    void existsBySku_Success() {
        when(jpaProductRepository.existsByCdSku("123")).thenReturn(true);
        boolean exists = productRepositoryAdapter.existsBySkuCode("123");
        assertTrue(exists);
        verify(jpaProductRepository).existsByCdSku("123");
    }
}
