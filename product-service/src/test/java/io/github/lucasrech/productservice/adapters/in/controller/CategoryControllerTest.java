package io.github.lucasrech.productservice.adapters.in.controller;

import io.github.lucasrech.productservice.application.usecases.CategoryUseCase;
import io.github.lucasrech.productservice.domain.category.Category;
import io.github.lucasrech.productservice.domain.category.CategoryRequestDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryUseCase categoryUseCase;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    @DisplayName("Should return 204 No Content when creating category")
    void createCategory_Success() {
        CategoryRequestDTO dto = new CategoryRequestDTO(null, "Eletronics", true);

        doNothing().when(categoryUseCase).insert(any(CategoryRequestDTO.class));

        ResponseEntity<Void> response = categoryController.createCategory(dto);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(categoryUseCase).insert(dto);
    }

    @Test
    @DisplayName("Should return 204 No Content when updating category")
    void updateCategory_Success() {
        Integer id = 1;
        CategoryRequestDTO dto = new CategoryRequestDTO(null, "Eletronics", true);

        doNothing().when(categoryUseCase).update(id, dto);

        ResponseEntity<Void> response = categoryController.updateCategory(id, dto);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(categoryUseCase).update(id, dto);
    }

    @Test
    @DisplayName("Should return 204 No Content when deleting category")
    void deleteCategory_Success() {
        Integer id = 1;

        doNothing().when(categoryUseCase).delete(id);

        ResponseEntity<Void> response = categoryController.deleteCategoryById(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(categoryUseCase).delete(id);
    }

    @Test
    @DisplayName("Should return 200 OK and list of categories")
    void findAllCategories_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Category category = new Category();
        Page<Category> page = new PageImpl<>(List.of(category));

        when(categoryUseCase.findAll(pageable, true)).thenReturn(page);

        ResponseEntity<Page<Category>> response = categoryController.findAllCategories(true, pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
        verify(categoryUseCase).findAll(pageable, true);
    }

    @Test
    @DisplayName("Should return 200 OK and find by Id")
    void findCategoryById_Success() {
        Integer id = 1;
        Category category = new Category();
        
        when(categoryUseCase.findById(id)).thenReturn(category);

        ResponseEntity<Category> response = categoryController.findCategoryById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(category, response.getBody());
        verify(categoryUseCase).findById(id);
    }
}
