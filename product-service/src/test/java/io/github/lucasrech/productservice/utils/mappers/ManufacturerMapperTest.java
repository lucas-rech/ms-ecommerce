package io.github.lucasrech.productservice.utils.mappers;

import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaManufacturerEntity;
import io.github.lucasrech.productservice.domain.manufacturer.Manufacturer;
import io.github.lucasrech.productservice.domain.manufacturer.ManufacturerRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ManufacturerMapperTest {

    @Test
    @DisplayName("Should map entity to domain")
    void toDomain_Success() {
        JpaManufacturerEntity entity = new JpaManufacturerEntity();
        entity.setId((short) 1);
        entity.setCompanyName("Apple Inc.");
        entity.setTradeName("Apple");
        entity.setCnpj("12345678901234");
        entity.setInclusionDate(LocalDateTime.now());
        entity.setUpdateDate(LocalDateTime.now());

        Manufacturer domain = ManufacturerMapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals((short) 1, domain.getId());
        assertEquals("Apple Inc.", domain.getCompanyName());
        assertEquals("Apple", domain.getTradeName());
        assertEquals("12345678901234", domain.getCnpj());
    }

    @Test
    @DisplayName("Should return null when mapping null entity")
    void toDomain_Null_Success() {
        assertNull(ManufacturerMapper.toDomain(null));
    }

    @Test
    @DisplayName("Should map domain to entity")
    void toEntity_Success() {
        Manufacturer domain = new Manufacturer((short) 1, "Apple", "Apple Inc.", "12345678901234", LocalDateTime.now(), LocalDateTime.now());

        JpaManufacturerEntity entity = ManufacturerMapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals((short) 1, entity.getId());
        assertEquals("Apple Inc.", entity.getCompanyName());
        assertEquals("Apple", entity.getTradeName());
        assertEquals("12345678901234", entity.getCnpj());
    }

    @Test
    @DisplayName("Should return null when mapping null domain")
    void toEntity_Null_Success() {
        assertNull(ManufacturerMapper.toEntity(null));
    }

    @Test
    @DisplayName("Should map dto to domain")
    void dtoToDomain_Success() {
        ManufacturerRequestDTO dto = new ManufacturerRequestDTO("Apple", "Apple Inc.", "12345678901234", true);

        Manufacturer domain = ManufacturerMapper.dtoToDomain(dto);

        assertNotNull(domain);
        assertEquals("Apple Inc.", domain.getCompanyName());
        assertEquals("Apple", domain.getTradeName());
        assertEquals("12345678901234", domain.getCnpj());
        assertTrue(domain.isActive());
    }

    @Test
    @DisplayName("Should return null when mapping null DTO")
    void dtoToDomain_Null_Success() {
        assertNull(ManufacturerMapper.dtoToDomain(null));
    }
}
