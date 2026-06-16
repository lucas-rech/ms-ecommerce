package io.github.lucasrech.productservice.adapters.out.persistence.repositories;

import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaManufacturerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaManufacturerRepository extends JpaRepository<JpaManufacturerEntity, Integer> {
    Optional<JpaManufacturerEntity> findByCnpj(String cnpj);
    boolean existsByCnpj(String cnpj);
}
