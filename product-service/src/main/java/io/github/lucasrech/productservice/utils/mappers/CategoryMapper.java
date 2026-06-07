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
                entity.getDescription()
        );
    }

    public static JpaCategoryEntity toEntity(Category domain) {
        if (domain == null) {
            return null;
        }

        JpaCategoryEntity entity = new JpaCategoryEntity();

        entity.setId(domain.getId());
        entity.setParentCategory(toEntity(domain.getParentCategory()));
        entity.setDescription(entity.getDescription());

        return entity;
    }
}
