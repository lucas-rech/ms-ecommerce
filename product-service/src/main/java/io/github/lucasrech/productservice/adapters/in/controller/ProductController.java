package io.github.lucasrech.productservice.adapters.in.controller;

import io.github.lucasrech.productservice.application.usecases.ProductUseCase;
import io.github.lucasrech.productservice.domain.product.Product;
import io.github.lucasrech.productservice.domain.product.ProductRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/backoffice/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductUseCase productUseCase;

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody @Valid ProductRequestDTO requestDTO) {
        productUseCase.insert(requestDTO);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable Long productId,
            @RequestBody @Valid ProductRequestDTO requestDTO) {
        productUseCase.update(productId, requestDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long productId) {
        productUseCase.delete(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<Product>> findAllProducts(
            @RequestParam(required = false) Boolean active,
            @PageableDefault(size = 15, sort = "inclusionDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(productUseCase.findAll(pageable, active));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> findProductById(@PathVariable Long productId) {
        return ResponseEntity.ok(productUseCase.findById(productId));
    }
}
