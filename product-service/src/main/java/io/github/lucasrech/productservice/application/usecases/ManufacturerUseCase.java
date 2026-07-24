package io.github.lucasrech.productservice.application.usecases;

import io.github.lucasrech.productservice.domain.manufacturer.Manufacturer;
import io.github.lucasrech.productservice.domain.manufacturer.ManufacturerRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ManufacturerUseCase {
    void insert(ManufacturerRequestDTO manufacturer);
    void update(Short id, ManufacturerRequestDTO manufacturer);
    void delete(Short id);
    Manufacturer findById(Short id);
    Page<Manufacturer> findAll(Pageable pageable);
    Manufacturer findByCnpj(String cnpj);
}
