package io.github.lucasrech.productservice.utils.mappers;

import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaCategoryEntity;
import io.github.lucasrech.productservice.domain.category.Category;
import io.github.lucasrech.productservice.domain.category.CategoryRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    @Test
    @DisplayName("Should map entity to domain")
    void toDomain_Success() {
        JpaCategoryEntity parentEntity = new JpaCategoryEntity();
        parentEntity.setId(1);
        parentEntity.setDescription("Eletronics");

        JpaCategoryEntity entity = new JpaCategoryEntity();
        entity.setId(2);
        entity.setDescription("Smartphones");
        entity.setParentCategory(parentEntity);
        entity.setActive(true);
        entity.setInclusionDate(LocalDateTime.now());

        Category domain = CategoryMapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(2, domain.getId());
        assertEquals("Smartphones", domain.getDescription());
        assertTrue(domain.isActive());
        assertEquals(1, domain.getParentCategory().getId());
    }

    @Test
    @DisplayName("Should map domain to entity")
    void toEntity_Success() {
        Category parentDomain = new Category();
        parentDomain.setId(1);
        parentDomain.setDescription("Eletronics");

        Category domain = new Category();
        domain.setId(2);
        domain.setDescription("Smartphones");
        domain.setParentCategory(parentDomain);
        domain.setActive(true);
        domain.setInclusionDate(LocalDateTime.now());

        JpaCategoryEntity entity = CategoryMapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals(2, entity.getId());
        assertEquals("Smartphones", entity.getDescription());
        assertTrue(entity.isActive());
        assertEquals(1, entity.getParentCategory().getId());
    }

    @Test
    @DisplayName("Should map DTO to domain")
    void dtoToDomain_Success() {
        CategoryRequestDTO dto = new CategoryRequestDTO(1, "Smartphones", true);

        Category domain = CategoryMapper.dtoToDomain(dto);

        assertNotNull(domain);
        assertEquals("Smartphones", domain.getDescription());
        assertTrue(domain.isActive());
    }
}
