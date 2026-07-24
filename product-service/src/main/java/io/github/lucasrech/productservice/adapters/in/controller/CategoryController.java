package io.github.lucasrech.productservice.adapters.in.controller;

import io.github.lucasrech.productservice.application.usecases.CategoryUseCase;
import io.github.lucasrech.productservice.domain.category.Category;
import io.github.lucasrech.productservice.domain.category.CategoryRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryUseCase categoryUseCase;

    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody @Valid CategoryRequestDTO requestDTO) {
        categoryUseCase.insert(requestDTO);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<Void> updateCategory(
            @PathVariable Integer categoryId,
            @RequestBody @Valid CategoryRequestDTO requestDTO) {
        categoryUseCase.update(categoryId, requestDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Integer categoryId) {
        categoryUseCase.delete(categoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<Category>> findAllCategories(
            @RequestParam(required = false) Boolean active,
            @PageableDefault(size = 15, sort = "inclusionDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(categoryUseCase.findAll(pageable, active));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> findCategoryById(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(categoryUseCase.findById(categoryId));
    }
}
