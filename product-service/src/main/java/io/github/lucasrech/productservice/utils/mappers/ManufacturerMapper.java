package io.github.lucasrech.productservice.utils.mappers;

import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaManufacturerEntity;
import io.github.lucasrech.productservice.domain.manufacturer.Manufacturer;

public class ManufacturerMapper {

    public static Manufacturer toDomain(JpaManufacturerEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Manufacturer(
                entity.getId(),
                entity.getTradeName(),
                entity.getCompanyName(),
                entity.getCnpj()
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

        return entity;
    }
}
