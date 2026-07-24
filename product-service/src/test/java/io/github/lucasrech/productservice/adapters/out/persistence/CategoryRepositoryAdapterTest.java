package io.github.lucasrech.productservice.adapters.out.persistence;

import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaCategoryEntity;
import io.github.lucasrech.productservice.adapters.out.persistence.repositories.JpaCategoryRepository;
import io.github.lucasrech.productservice.domain.category.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryRepositoryAdapterTest {

    @Mock
    private JpaCategoryRepository jpaCategoryRepository;

    @InjectMocks
    private CategoryRepositoryAdapter categoryRepositoryAdapter;

    @Test
    @DisplayName("Should save category")
    void save_Success() {
        Category domain = new Category();
        domain.setId(1);
        
        JpaCategoryEntity entity = new JpaCategoryEntity();
        entity.setId(1);

        when(jpaCategoryRepository.save(any(JpaCategoryEntity.class))).thenReturn(entity);

        Category result = categoryRepositoryAdapter.save(domain);

        assertEquals(1, result.getId());
        verify(jpaCategoryRepository).save(any(JpaCategoryEntity.class));
    }

    @Test
    @DisplayName("Should find by id")
    void findById_Success() {
        JpaCategoryEntity entity = new JpaCategoryEntity();
        entity.setId(1);

        when(jpaCategoryRepository.findById(1)).thenReturn(Optional.of(entity));

        Optional<Category> result = categoryRepositoryAdapter.findById(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
        verify(jpaCategoryRepository).findById(1);
    }

    @Test
    @DisplayName("Should find all categories")
    void findAll_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        JpaCategoryEntity entity = new JpaCategoryEntity();
        entity.setId(1);
        Page<JpaCategoryEntity> page = new PageImpl<>(List.of(entity));

        when(jpaCategoryRepository.findAll(pageable)).thenReturn(page);

        Page<Category> result = categoryRepositoryAdapter.findAll(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getContent().get(0).getId());
        verify(jpaCategoryRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Should find all active categories")
    void findAllByIsActive_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        JpaCategoryEntity entity = new JpaCategoryEntity();
        entity.setId(1);
        Page<JpaCategoryEntity> page = new PageImpl<>(List.of(entity));

        when(jpaCategoryRepository.findAllByIsActive(eq(true), eq(pageable))).thenReturn(page);

        Page<Category> result = categoryRepositoryAdapter.findAllByIsActive(true, pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getContent().get(0).getId());
        verify(jpaCategoryRepository).findAllByIsActive(eq(true), eq(pageable));
    }
}
