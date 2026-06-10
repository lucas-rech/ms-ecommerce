package io.github.lucasrech.productservice.utils.mappers;

import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaCategoryEntity;
import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaManufacturerEntity;
import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaProductEntity;
import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaProductImageEntity;
import io.github.lucasrech.productservice.domain.product.Product;

public class ProductMapper {

    public static Product toDomain(JpaProductEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Product(
                entity.getId(),
                entity.getCdSku(),
                entity.getCdEan(),
                entity.getName(),
                entity.getDescription(),
                ManufacturerMapper.toDomain(entity.getManufacturer()),
                entity.getPrice(),
                entity.getCategories() != null ? entity.getCategories().stream().map(CategoryMapper::toDomain).toList() : null,
                entity.getImages() != null ? entity.getImages().stream().map(ProductImageMapper::toDomain).toList() : null,
                entity.getInclusionDate(),
                entity.getUpdateDate()
        );
    }

    public static JpaProductEntity toEntity(Product domain) {
        if (domain == null) {
            return null;
        }

        JpaProductEntity entity = new JpaProductEntity();

        entity.setId(domain.getId());
        entity.setCdSku(domain.getSkuCode());
        entity.setCdEan(domain.getEanCode());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setPrice(domain.getPrice());
        entity.setInclusionDate(domain.getInclusionDate());
        entity.setUpdateDate(domain.getUpdateDate());
        entity.setActive(domain.isActive());

        if (domain.getManufacturer() != null && domain.getManufacturer().getId() != null) {
            JpaManufacturerEntity manufacturerRef = new JpaManufacturerEntity();
            manufacturerRef.setId(domain.getManufacturer().getId());
            entity.setManufacturer(manufacturerRef);
        } else {
            entity.setManufacturer(null);
        }

        if (domain.getImages() != null) {
            entity.setImages(domain.getImages().stream()
                    .map(imgDomain -> {
                        JpaProductImageEntity imgEntity = ProductImageMapper.toEntity(imgDomain);

                        imgEntity.setProduct(entity);

                        return imgEntity;
                    })
                    .toList());
        } else {
            entity.setImages(null);
        }

        if (domain.getCategories() != null) {
            entity.setCategories(domain.getCategories().stream()
                    .map(categoryDomain -> {
                        JpaCategoryEntity categoryRef = new JpaCategoryEntity();
                        categoryRef.setId(categoryDomain.getId());
                        return categoryRef;
                    })
                    .toList());
        } else {
            entity.setCategories(null);
        }

        return entity;
    }
}
