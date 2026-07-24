package io.github.lucasrech.productservice.utils.mappers;

import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaProductEntity;
import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaProductImageEntity;
import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaManufacturerEntity;
import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaCategoryEntity;
import io.github.lucasrech.productservice.domain.product.Product;
import io.github.lucasrech.productservice.domain.product.ProductImage;
import io.github.lucasrech.productservice.domain.manufacturer.Manufacturer;
import io.github.lucasrech.productservice.domain.category.Category;
import io.github.lucasrech.productservice.domain.product.ProductRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

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
        
        JpaManufacturerEntity manufacturer = new JpaManufacturerEntity();
        manufacturer.setId((short) 1);
        entity.setManufacturer(manufacturer);
        
        JpaCategoryEntity category = new JpaCategoryEntity();
        category.setId(1);
        entity.setCategories(List.of(category));
        
        JpaProductImageEntity image = new JpaProductImageEntity();
        image.setId(1L);
        entity.setImages(List.of(image));

        Product domain = ProductMapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals("SKU", domain.getSkuCode());
        assertEquals("EAN", domain.getEanCode());
        assertEquals("Phone", domain.getName());
        assertEquals(BigDecimal.TEN, domain.getPrice());
        assertTrue(domain.isActive());
        assertNotNull(domain.getManufacturer());
        assertNotNull(domain.getCategories());
        assertNotNull(domain.getImages());
    }

    @Test
    @DisplayName("Should return null when mapping null entity")
    void toDomain_Null_Success() {
        assertNull(ProductMapper.toDomain(null));
    }

    @Test
    @DisplayName("Should map domain to entity")
    void toEntity_Success() {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId((short) 1);
        
        Category category = new Category();
        category.setId(1);
        
        ProductImage image = new ProductImage(1L, "KEY", true, null, null);

        Product domain = new Product(1L, "SKU", "EAN", "Phone", "Desc", manufacturer, BigDecimal.TEN, List.of(category), List.of(image), null, null, true);

        JpaProductEntity entity = ProductMapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("SKU", entity.getCdSku());
        assertEquals("EAN", entity.getCdEan());
        assertEquals("Phone", entity.getName());
        assertEquals("Desc", entity.getDescription());
        assertEquals(BigDecimal.TEN, entity.getPrice());
        assertTrue(entity.isActive());
        assertNotNull(entity.getManufacturer());
        assertNotNull(entity.getCategories());
        assertNotNull(entity.getImages());
    }

    @Test
    @DisplayName("Should return null when mapping null domain")
    void toEntity_Null_Success() {
        assertNull(ProductMapper.toEntity(null));
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

    @Test
    @DisplayName("Should return null when mapping null DTO")
    void dtoToDomain_Null_Success() {
        assertNull(ProductMapper.dtoToDomain(null));
    }
}
