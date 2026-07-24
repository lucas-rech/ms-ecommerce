package io.github.lucasrech.productservice.utils.mappers;

import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaManufacturerEntity;
import io.github.lucasrech.productservice.domain.manufacturer.Manufacturer;
import io.github.lucasrech.productservice.domain.manufacturer.ManufacturerRequestDTO;

public class ManufacturerMapper {

    public static Manufacturer toDomain(JpaManufacturerEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Manufacturer(
                entity.getId(),
                entity.getTradeName(),
                entity.getCompanyName(),
                entity.getCnpj(),
                entity.getInclusionDate(),
                entity.getUpdateDate()
        );
    }

    public static JpaManufacturerEntity toEntity(Manufacturer domain) {
        if (domain == null) {
            return null;
        }

        JpaManufacturerEntity entity = new JpaManufacturerEntity();

        entity.setId(domain.getId());
        entity.setCompanyName(domain.getCompanyName());
        entity.setTradeName(domain.getTradeName());
        entity.setCnpj(domain.getCnpj());
        entity.setInclusionDate(domain.getInclusionDate());
        entity.setUpdateDate(domain.getUpdateDate());

        return entity;
    }

    public static Manufacturer dtoToDomain(ManufacturerRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }

        Manufacturer domain = new Manufacturer();

        domain.setTradeName(requestDTO.tradeName());
        domain.setCompanyName(requestDTO.companyName());
        domain.setCnpj(requestDTO.cnpj());
        domain.setActive(requestDTO.isActive() != null ? requestDTO.isActive() : true);

        return domain;
    }
}
