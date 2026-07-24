package io.github.lucasrech.productservice.adapters.out.persistence.repositories;

import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaCategoryRepository extends JpaRepository<JpaCategoryEntity, Integer> {
    List<JpaCategoryEntity> findAllByParentCategory(JpaCategoryEntity parentCategory);
    org.springframework.data.domain.Page<JpaCategoryEntity> findAllByIsActive(boolean isActive, org.springframework.data.domain.Pageable pageable);
}
