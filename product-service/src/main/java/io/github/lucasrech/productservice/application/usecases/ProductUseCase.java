package io.github.lucasrech.productservice.application.usecases;

import io.github.lucasrech.productservice.domain.product.Product;
import io.github.lucasrech.productservice.domain.product.ProductRequestDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductUseCase {
    void insert(ProductRequestDTO productDTO);
    void update(Long id, ProductRequestDTO productDTO);
    void delete(Long id);
    Product findById(Long id);
    Page<Product> findAll(Pageable pageable, Boolean active);
    Page<Product> findAllByManufacturer(Short manufacturerId, Pageable pageable);
}
