package io.github.lucasrech.productservice.adapters.out.persistence;

import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaManufacturerEntity;
import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaProductEntity;
import io.github.lucasrech.productservice.adapters.out.persistence.repositories.JpaProductRepository;
import io.github.lucasrech.productservice.domain.manufacturer.Manufacturer;
import io.github.lucasrech.productservice.domain.product.Product;
import io.github.lucasrech.productservice.domain.product.ProductRepository;
import io.github.lucasrech.productservice.utils.mappers.ManufacturerMapper;
import io.github.lucasrech.productservice.utils.mappers.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {
    private final JpaProductRepository jpaProductRepository;

    @Override
    public Product save(Product product) {
        JpaProductEntity entityToSave = ProductMapper.toEntity(product);

        JpaProductEntity savedEntity = jpaProductRepository.save(entityToSave);

        return ProductMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpaProductRepository.findById(id)
                .map(ProductMapper::toDomain);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return jpaProductRepository
                .findAll(pageable)
                .map(ProductMapper::toDomain);
    }

    @Override
    public Page<Product> findAllByManufacturer(Manufacturer manufacturer, Pageable pageable) {
        JpaManufacturerEntity manufacturerEntity = ManufacturerMapper.toEntity(manufacturer);

        return jpaProductRepository
                .findAllByManufacturer(manufacturerEntity, pageable)
                .map(ProductMapper::toDomain);
    }

    @Override
    public Page<Product> findAllByIsActive(boolean active, Pageable pageable) {
        return jpaProductRepository.findAllByIsActive(active, pageable)
                .map(ProductMapper::toDomain);
    }

    @Override
    public Optional<Product> findByEanCode(String ean) {
        return jpaProductRepository.findByCdEan(ean)
                .map(ProductMapper::toDomain);
    }

    @Override
    public Optional<Product> findBySkuCode(String sku) {
        return jpaProductRepository.findByCdSku(sku)
                .map(ProductMapper::toDomain);
    }

    @Override
    public boolean existsByEanCode(String ean) {
        return jpaProductRepository.existsByCdEan(ean);
    }

    @Override
    public boolean existsBySkuCode(String sku) {
        return jpaProductRepository.existsByCdSku(sku);
    }
}
