package io.github.lucasrech.productservice.adapters.out.persistence.repositories;

import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaManufacturerEntity;
import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JpaProductRepository extends JpaRepository<JpaProductEntity, Long> {
    Page<JpaProductEntity> findAllByManufacturer(JpaManufacturerEntity manufacturer, Pageable pageable);

}
