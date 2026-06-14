package io.github.lucasrech.productservice.application.usecases;

import io.github.lucasrech.productservice.domain.manufacturer.Manufacturer;
import io.github.lucasrech.productservice.domain.manufacturer.ManufacturerRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ManufacturerUseCase {
    void insert(ManufacturerRequestDTO manufacturer);
    void update(Integer id, ManufacturerRequestDTO manufacturer);
    void delete(Integer id);
    Manufacturer finById(Integer id);
    Page<Manufacturer> findAll(Pageable pageable);
    Optional<Manufacturer> findByCnpj(String cnpj);
}
