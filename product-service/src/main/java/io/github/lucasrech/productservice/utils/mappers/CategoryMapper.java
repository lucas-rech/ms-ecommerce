package io.github.lucasrech.productservice.utils.mappers;

import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaCategoryEntity;
import io.github.lucasrech.productservice.domain.category.Category;

public class CategoryMapper {

    public static Category toDomain(JpaCategoryEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Category(
                entity.getId(),
                toDomain(entity.getParentCategory()),
                entity.getDescription(),
                entity.getInclusionDate(),
                entity.getUpdateDate(),
                entity.isActive()
        );
    }

    public static JpaCategoryEntity toEntity(Category domain) {
        if (domain == null) {
            return null;
        }

        JpaCategoryEntity entity = new JpaCategoryEntity();

        entity.setId(domain.getId());
        entity.setParentCategory(toEntity(domain.getParentCategory()));
        entity.setDescription(domain.getDescription());
        entity.setInclusionDate(domain.getInclusionDate());
        entity.setUpdateDate(domain.getUpdateDate());
        entity.setActive(domain.isActive());

        return entity;
    }

    public static Category dtoToDomain(io.github.lucasrech.productservice.domain.category.CategoryRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Category category = new Category();
        category.setDescription(dto.description());
        category.setActive(dto.isActive() != null ? dto.isActive() : true);
        
        return category;
    }
}
