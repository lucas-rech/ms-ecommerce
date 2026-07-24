package io.github.lucasrech.productservice.application.usecases;

import io.github.lucasrech.productservice.domain.category.Category;
import io.github.lucasrech.productservice.domain.category.CategoryRequestDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryUseCase {
    void insert(CategoryRequestDTO categoryDTO);
    void update(Integer id, CategoryRequestDTO categoryDTO);
    void delete(Integer id);
    Category findById(Integer id);
    Page<Category> findAll(Pageable pageable, Boolean active);
}
