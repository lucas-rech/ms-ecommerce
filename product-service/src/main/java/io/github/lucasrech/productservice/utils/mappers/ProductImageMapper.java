package io.github.lucasrech.productservice.utils.mappers;

import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaProductImageEntity;
import io.github.lucasrech.productservice.domain.product.ProductImage;

public class ProductImageMapper {

    public static ProductImage toDomain(JpaProductImageEntity entity) {
        if(entity == null) {
            return null;
        }

        return new ProductImage(
                entity.getId(),
                entity.getS3Code(),
                entity.isFeatured(),
                entity.getInclusionDate(),
                entity.getUpdateDate()
        );
    }

    public static JpaProductImageEntity toEntity(ProductImage domain) {
        if (domain == null) {
            return null;
        }

        JpaProductImageEntity entity = new JpaProductImageEntity();

        entity.setId(domain.getId());
        entity.setS3Code(domain.getS3Code());
        entity.setFeatured(domain.isFeatured());
        entity.setInclusionDate(domain.getInclusionDate());
        entity.setUpdateDate(domain.getUpdateDate());

        return entity;
    }
}
