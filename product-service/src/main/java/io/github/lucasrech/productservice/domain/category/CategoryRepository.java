package io.github.lucasrech.productservice.domain.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryRepository {
    Category save(Category category);
    List<Category> findByParentCategory(Category parent);
    Page<Category> findAll(Pageable pageable);
}
