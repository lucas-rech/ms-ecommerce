package io.github.lucasrech.productservice.utils.mappers;

import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaProductImageEntity;
import io.github.lucasrech.productservice.domain.product.ProductImage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProductImageMapperTest {

    @Test
    @DisplayName("Should map entity to domain")
    void toDomain_Success() {
        JpaProductImageEntity entity = new JpaProductImageEntity();
        entity.setId(1L);
        entity.setS3Code("S3_KEY");
        entity.setFeatured(true);
        entity.setInclusionDate(LocalDateTime.now());
        entity.setUpdateDate(LocalDateTime.now());

        ProductImage domain = ProductImageMapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals("S3_KEY", domain.getS3Code());
        assertTrue(domain.isFeatured());
        assertNotNull(domain.getInclusionDate());
        assertNotNull(domain.getUpdateDate());
    }

    @Test
    @DisplayName("Should return null when mapping null entity")
    void toDomain_Null_Success() {
        assertNull(ProductImageMapper.toDomain(null));
    }

    @Test
    @DisplayName("Should map domain to entity")
    void toEntity_Success() {
        ProductImage domain = new ProductImage(1L, "S3_KEY", true, LocalDateTime.now(), LocalDateTime.now());

        JpaProductImageEntity entity = ProductImageMapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("S3_KEY", entity.getS3Code());
        assertTrue(entity.isFeatured());
        assertNotNull(entity.getInclusionDate());
        assertNotNull(entity.getUpdateDate());
    }

    @Test
    @DisplayName("Should return null when mapping null domain")
    void toEntity_Null_Success() {
        assertNull(ProductImageMapper.toEntity(null));
    }
}
