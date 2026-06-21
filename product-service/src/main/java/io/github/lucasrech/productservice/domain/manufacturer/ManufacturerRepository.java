package io.github.lucasrech.productservice.domain.manufacturer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ManufacturerRepository {
    Manufacturer save(Manufacturer manufacturer);
    Optional<Manufacturer> findById(Short id);
    Page<Manufacturer> findAll(Pageable pageable);
    Optional<Manufacturer> findByCnpj(String cnpj);
    boolean existsByCnpj(String cnpj);
}
