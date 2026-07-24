package io.github.lucasrech.productservice.adapters.out.persistence;

import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaCategoryEntity;
import io.github.lucasrech.productservice.adapters.out.persistence.repositories.JpaCategoryRepository;
import io.github.lucasrech.productservice.domain.category.Category;
import io.github.lucasrech.productservice.domain.category.CategoryRepository;
import io.github.lucasrech.productservice.utils.mappers.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepository {
    private final JpaCategoryRepository jpaCategoryRepository;

    @Override
    public Category save(Category category) {
        JpaCategoryEntity entityToSave = CategoryMapper.toEntity(category);

        JpaCategoryEntity savedEntity = jpaCategoryRepository.save(entityToSave);

        return CategoryMapper.toDomain(savedEntity);
    }

    @Override
    public List<Category> findByParentCategory(Category parent) {
        JpaCategoryEntity parentEntity = CategoryMapper.toEntity(parent);

        return jpaCategoryRepository.findAllByParentCategory(parentEntity).stream()
                .map(CategoryMapper::toDomain).toList();
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return jpaCategoryRepository.findAll(pageable)
                .map(CategoryMapper::toDomain);
    }
    @Override
    public java.util.Optional<Category> findById(Integer id) {
        return jpaCategoryRepository.findById(id).map(CategoryMapper::toDomain);
    }

    @Override
    public Page<Category> findAllByIsActive(boolean active, Pageable pageable) {
        return jpaCategoryRepository.findAllByIsActive(active, pageable)
                .map(CategoryMapper::toDomain);
    }
}
