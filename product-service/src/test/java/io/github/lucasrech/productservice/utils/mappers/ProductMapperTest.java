package io.github.lucasrech.productservice.utils.mappers;

import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaProductEntity;
import io.github.lucasrech.productservice.domain.product.Product;
import io.github.lucasrech.productservice.domain.product.ProductRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    @Test
    @DisplayName("Should map entity to domain")
    void toDomain_Success() {
        JpaProductEntity entity = new JpaProductEntity();
        entity.setId(1L);
        entity.setCdSku("SKU");
        entity.setCdEan("EAN");
        entity.setName("Phone");
        entity.setPrice(BigDecimal.TEN);
        entity.setActive(true);

        Product domain = ProductMapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals("SKU", domain.getSkuCode());
        assertEquals("EAN", domain.getEanCode());
        assertEquals("Phone", domain.getName());
        assertEquals(BigDecimal.TEN, domain.getPrice());
        assertTrue(domain.isActive());
    }

    @Test
    @DisplayName("Should map domain to entity")
    void toEntity_Success() {
        Product domain = new Product(1L, "SKU", "EAN", "Phone", "Desc", null, BigDecimal.TEN, null, null, null, null, true);

        JpaProductEntity entity = ProductMapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("SKU", entity.getCdSku());
        assertEquals("EAN", entity.getCdEan());
        assertEquals("Phone", entity.getName());
        assertEquals("Desc", entity.getDescription());
        assertEquals(BigDecimal.TEN, entity.getPrice());
        assertTrue(entity.isActive());
    }

    @Test
    @DisplayName("Should map DTO to domain")
    void dtoToDomain_Success() {
        ProductRequestDTO dto = new ProductRequestDTO(
                "Phone", "Desc", "EAN", "SKU", BigDecimal.TEN, (short) 1, null, true
        );

        Product domain = ProductMapper.dtoToDomain(dto);

        assertNotNull(domain);
        assertEquals("Phone", domain.getName());
        assertEquals("Desc", domain.getDescription());
        assertEquals("EAN", domain.getEanCode());
        assertEquals("SKU", domain.getSkuCode());
        assertEquals(BigDecimal.TEN, domain.getPrice());
        assertTrue(domain.isActive());
    }
}
