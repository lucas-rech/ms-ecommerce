package io.github.lucasrech.productservice.domain.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import java.util.Optional;

public interface CategoryRepository {
    Category save(Category category);
    Optional<Category> findById(Integer id);
    List<Category> findByParentCategory(Category parent);
    Page<Category> findAll(Pageable pageable);
    Page<Category> findAllByIsActive(boolean active, Pageable pageable);
}
