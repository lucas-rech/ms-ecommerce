package io.github.lucasrech.productservice.application.services;

import io.github.lucasrech.productservice.domain.category.Category;
import io.github.lucasrech.productservice.domain.category.CategoryRepository;
import io.github.lucasrech.productservice.domain.category.CategoryRequestDTO;
import io.github.lucasrech.productservice.utils.exception.BusinessException;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("Should insert a valid category without parent")
    void insert_SuccessWithoutParent() {
        CategoryRequestDTO dto = new CategoryRequestDTO(null, "Electronics", true);
        
        when(categoryRepository.save(any(Category.class))).thenReturn(new Category());

        categoryService.insert(dto);

        verify(categoryRepository, never()).findById(any());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    @DisplayName("Should insert a valid category with parent")
    void insert_SuccessWithParent() {
        CategoryRequestDTO dto = new CategoryRequestDTO(1, "Smartphones", true);
        Category parent = new Category(1, null, "Electronics", LocalDateTime.now(), LocalDateTime.now(), true);
        
        when(categoryRepository.findById(1)).thenReturn(Optional.of(parent));
        when(categoryRepository.save(any(Category.class))).thenReturn(new Category());

        categoryService.insert(dto);

        verify(categoryRepository).findById(1);
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    @DisplayName("Should throw BusinessException when inserting with null DTO")
    void insert_NullDTO_ThrowsException() {
        assertThrows(BusinessException.class, () -> categoryService.insert(null));
    }

    @Test
    @DisplayName("Should throw BusinessException when inserting with invalid parent")
    void insert_InvalidParent_ThrowsException() {
        CategoryRequestDTO dto = new CategoryRequestDTO(1, "Smartphones", true);
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> categoryService.insert(dto));
    }

    @Test
    @DisplayName("Should update an existing category")
    void update_Success() {
        Integer id = 2;
        CategoryRequestDTO dto = new CategoryRequestDTO(1, "Updated Smartphones", true);
        Category existingCategory = new Category(2, null, "Smartphones", LocalDateTime.now(), LocalDateTime.now(), true);
        Category parent = new Category(1, null, "Electronics", LocalDateTime.now(), LocalDateTime.now(), true);
        
        when(categoryRepository.findById(2)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(parent));
        when(categoryRepository.save(any(Category.class))).thenReturn(existingCategory);

        categoryService.update(id, dto);

        assertEquals("Updated Smartphones", existingCategory.getDescription());
        assertEquals(parent, existingCategory.getParentCategory());
        verify(categoryRepository).save(existingCategory);
    }

    @Test
    @DisplayName("Should update an existing category and remove parent")
    void update_RemoveParent_Success() {
        Integer id = 2;
        CategoryRequestDTO dto = new CategoryRequestDTO(null, "Updated Smartphones", true);
        Category parent = new Category(1, null, "Electronics", LocalDateTime.now(), LocalDateTime.now(), true);
        Category existingCategory = new Category(2, parent, "Smartphones", LocalDateTime.now(), LocalDateTime.now(), true);
        
        when(categoryRepository.findById(2)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(existingCategory);

        categoryService.update(id, dto);

        assertEquals("Updated Smartphones", existingCategory.getDescription());
        assertNull(existingCategory.getParentCategory());
        verify(categoryRepository).save(existingCategory);
    }

    @Test
    @DisplayName("Should throw BusinessException when updating category with itself as parent")
    void update_CategoryParentItself_ThrowsException() {
        Integer id = 2;
        CategoryRequestDTO dto = new CategoryRequestDTO(2, "Updated Smartphones", true);
        Category existingCategory = new Category(2, null, "Smartphones", LocalDateTime.now(), LocalDateTime.now(), true);
        
        when(categoryRepository.findById(2)).thenReturn(Optional.of(existingCategory));

        assertThrows(BusinessException.class, () -> categoryService.update(id, dto));
    }

    @Test
    @DisplayName("Should throw BusinessException when updating with null id or dto")
    void update_NullIdOrDTO_ThrowsException() {
        assertThrows(BusinessException.class, () -> categoryService.update(null, new CategoryRequestDTO(1, "Desc", true)));
        assertThrows(BusinessException.class, () -> categoryService.update(1, null));
    }

    @Test
    @DisplayName("Should throw BusinessException when updating non-existent category")
    void update_NotFound_ThrowsException() {
        CategoryRequestDTO dto = new CategoryRequestDTO(1, "Updated Smartphones", true);
        when(categoryRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> categoryService.update(2, dto));
    }

    @Test
    @DisplayName("Should throw BusinessException when updating with invalid parent")
    void update_InvalidParent_ThrowsException() {
        CategoryRequestDTO dto = new CategoryRequestDTO(1, "Updated Smartphones", true);
        Category existingCategory = new Category(2, null, "Smartphones", LocalDateTime.now(), LocalDateTime.now(), true);
        
        when(categoryRepository.findById(2)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> categoryService.update(2, dto));
    }

    @Test
    @DisplayName("Should perform soft delete on category")
    void delete_Success() {
        Integer id = 1;
        Category existingCategory = new Category(1, null, "Electronics", LocalDateTime.now(), LocalDateTime.now(), true);
        
        when(categoryRepository.findById(id)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(existingCategory);

        categoryService.delete(id);

        assertFalse(existingCategory.isActive());
        verify(categoryRepository).save(existingCategory);
    }

    @Test
    @DisplayName("Should throw BusinessException when deleting null id")
    void delete_NullId_ThrowsException() {
        assertThrows(BusinessException.class, () -> categoryService.delete(null));
    }

    @Test
    @DisplayName("Should throw BusinessException when deleting non-existent category")
    void delete_NotFound_ThrowsException() {
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> categoryService.delete(1));
    }

    @Test
    @DisplayName("Should find active categories")
    void findAll_ActiveOnly() {
        Pageable pageable = PageRequest.of(0, 10);
        Category category = new Category();
        Page<Category> page = new PageImpl<>(List.of(category));

        when(categoryRepository.findAllByIsActive(true, pageable)).thenReturn(page);

        Page<Category> result = categoryService.findAll(pageable, true);

        assertEquals(page, result);
        verify(categoryRepository).findAllByIsActive(true, pageable);
    }

    @Test
    @DisplayName("Should find all categories (active and inactive)")
    void findAll_All() {
        Pageable pageable = PageRequest.of(0, 10);
        Category category = new Category();
        Page<Category> page = new PageImpl<>(List.of(category));

        when(categoryRepository.findAll(pageable)).thenReturn(page);

        Page<Category> result = categoryService.findAll(pageable, null);

        assertEquals(page, result);
        verify(categoryRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Should find category by id")
    void findById_Success() {
        Category category = new Category();
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

        Category result = categoryService.findById(1);

        assertEquals(category, result);
    }

    @Test
    @DisplayName("Should throw BusinessException when finding null id")
    void findById_NullId_ThrowsException() {
        assertThrows(BusinessException.class, () -> categoryService.findById(null));
    }

    @Test
    @DisplayName("Should throw BusinessException when finding non-existent category")
    void findById_NotFound_ThrowsException() {
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> categoryService.findById(1));
    }
}
