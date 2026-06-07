package io.github.lucasrech.productservice.domain.product;

import io.github.lucasrech.productservice.domain.manufacturer.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    Page<Product> findAll(Pageable pageable);
    Page<Product> findAllByManufacturer(Manufacturer manufacturer, Pageable pageable);

}
