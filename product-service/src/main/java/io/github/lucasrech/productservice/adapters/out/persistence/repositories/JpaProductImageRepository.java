package io.github.lucasrech.productservice.adapters.out.persistence.repositories;

import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductImageRepository extends JpaRepository<JpaProductImageEntity, Long> {
}
