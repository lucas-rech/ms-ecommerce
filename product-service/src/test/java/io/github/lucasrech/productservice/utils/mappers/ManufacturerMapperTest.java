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
        entity.setTradeName("Trade");
        entity.setCompanyName("Company");
        entity.setCnpj("12345678901234");
        entity.setInclusionDate(LocalDateTime.now());
        entity.setUpdateDate(LocalDateTime.now());

        Manufacturer domain = ManufacturerMapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getTradeName(), domain.getTradeName());
        assertEquals(entity.getCompanyName(), domain.getCompanyName());
        assertEquals(entity.getCnpj(), domain.getCnpj());
        assertEquals(entity.getInclusionDate(), domain.getInclusionDate());
        assertEquals(entity.getUpdateDate(), domain.getUpdateDate());
    }

    @Test
    @DisplayName("Should return null when mapping null entity to domain")
    void toDomain_NullEntity() {
        assertNull(ManufacturerMapper.toDomain(null));
    }

    @Test
    @DisplayName("Should map domain to entity")
    void toEntity_Success() {
        Manufacturer domain = new Manufacturer();
        domain.setId((short) 1);
        domain.setTradeName("Trade");
        domain.setCompanyName("Company");
        domain.setCnpj("12345678901234");
        domain.setInclusionDate(LocalDateTime.now());
        domain.setUpdateDate(LocalDateTime.now());

        JpaManufacturerEntity entity = ManufacturerMapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getTradeName(), entity.getTradeName());
        assertEquals(domain.getCompanyName(), entity.getCompanyName());
        assertEquals(domain.getCnpj(), entity.getCnpj());
        assertEquals(domain.getInclusionDate(), entity.getInclusionDate());
        assertEquals(domain.getUpdateDate(), entity.getUpdateDate());
    }

    @Test
    @DisplayName("Should return null when mapping null domain to entity")
    void toEntity_NullDomain() {
        assertNull(ManufacturerMapper.toEntity(null));
    }

    @Test
    @DisplayName("Should map DTO to domain")
    void dtoToDomain_Success() {
        ManufacturerRequestDTO dto = new ManufacturerRequestDTO("Trade", "Company", "12345678901234", true);

        Manufacturer domain = ManufacturerMapper.dtoToDomain(dto);

        assertNotNull(domain);
        assertEquals(dto.tradeName(), domain.getTradeName());
        assertEquals(dto.companyName(), domain.getCompanyName());
        assertEquals(dto.cnpj(), domain.getCnpj());
        assertTrue(domain.isActive());
    }

    @Test
    @DisplayName("Should return null when mapping null DTO to domain")
    void dtoToDomain_NullDto() {
        assertNull(ManufacturerMapper.dtoToDomain(null));
    }
}
