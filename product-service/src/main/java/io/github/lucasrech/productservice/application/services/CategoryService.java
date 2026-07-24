package io.github.lucasrech.productservice.application.services;

import io.github.lucasrech.productservice.application.usecases.CategoryUseCase;
import io.github.lucasrech.productservice.domain.category.Category;
import io.github.lucasrech.productservice.domain.category.CategoryRepository;
import io.github.lucasrech.productservice.domain.category.CategoryRequestDTO;
import io.github.lucasrech.productservice.utils.exception.BusinessError;
import io.github.lucasrech.productservice.utils.exception.BusinessException;
import io.github.lucasrech.productservice.utils.mappers.CategoryMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryUseCase {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void insert(CategoryRequestDTO categoryDTO) {
        if (categoryDTO == null) {
            throw BusinessError.REQUEST_NULLABLE_OBJECT.asException();
        }

        Category category = CategoryMapper.dtoToDomain(categoryDTO);
        
        if (categoryDTO.parentCategoryId() != null) {
            Category parent = categoryRepository.findById(categoryDTO.parentCategoryId())
                    .orElseThrow(() -> BusinessException.builder()
                            .message("Categoria pai não encontrada")
                            .description("Não foi possível encontrar a categoria pai informada")
                            .status(HttpStatus.NOT_FOUND)
                            .build());
            category.setParentCategory(parent);
        }

        categoryRepository.save(category);
        log.info("Categoria inserida com sucesso");
    }

    @Override
    @Transactional
    public void update(Integer id, CategoryRequestDTO categoryDTO) {
        if (id == null || categoryDTO == null) {
            throw BusinessError.REQUEST_NULLABLE_OBJECT.asException();
        }

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> BusinessException.builder()
                        .message("Categoria não encontrada")
                        .description("A categoria informada para atualização não existe")
                        .status(HttpStatus.NOT_FOUND)
                        .build());

        existingCategory.setDescription(categoryDTO.description());

        if (categoryDTO.parentCategoryId() != null) {
            if (categoryDTO.parentCategoryId().equals(id)) {
                throw BusinessException.builder()
                        .message("Categoria pai inválida")
                        .description("Uma categoria não pode ser pai dela mesma")
                        .status(HttpStatus.BAD_REQUEST)
                        .build();
            }

            Category parent = categoryRepository.findById(categoryDTO.parentCategoryId())
                    .orElseThrow(() -> BusinessException.builder()
                            .message("Categoria pai não encontrada")
                            .description("Não foi possível encontrar a categoria pai informada")
                            .status(HttpStatus.NOT_FOUND)
                            .build());
            existingCategory.setParentCategory(parent);
        } else {
            existingCategory.setParentCategory(null);
        }

        categoryRepository.save(existingCategory);
        log.info("Categoria atualizada com sucesso: {}", id);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw BusinessError.REQUEST_NULLABLE_OBJECT.asException();
        }

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> BusinessException.builder()
                        .message("Categoria não encontrada")
                        .description("A categoria informada para deleção não existe")
                        .status(HttpStatus.NOT_FOUND)
                        .build());

        existingCategory.setActive(false);
        categoryRepository.save(existingCategory);
        log.info("Categoria inativada (soft delete) com sucesso: {}", id);
    }

    @Override
    public Category findById(Integer id) {
        if (id == null) {
            throw BusinessError.REQUEST_NULLABLE_OBJECT.asException();
        }

        Category domain = categoryRepository.findById(id)
                .orElseThrow(() -> BusinessException.builder()
                        .message("Categoria não encontrada")
                        .description("A categoria informada não existe")
                        .status(HttpStatus.NOT_FOUND)
                        .build());

        log.info("Categoria encontrada e retornada com sucesso: {}", id);
        return domain;
    }

    @Override
    public Page<Category> findAll(Pageable pageable, Boolean active) {
        log.info("Listagem paginada de categorias solicitada");
        
        if (active != null) {
            return categoryRepository.findAllByIsActive(active, pageable);
        }
        
        return categoryRepository.findAll(pageable);
    }
}
