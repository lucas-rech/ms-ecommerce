package io.github.lucasrech.productservice.adapters.in.controller;

import io.github.lucasrech.productservice.application.usecases.ProductUseCase;
import io.github.lucasrech.productservice.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/frontstore/product")
@RequiredArgsConstructor
public class ProductFrontstoreController {

    private final ProductUseCase productUseCase;

    @GetMapping
    public ResponseEntity<Page<Product>> findAllProducts(
            @PageableDefault(size = 15, sort = "inclusionDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(productUseCase.findAll(pageable, true));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> findProductById(@PathVariable Long productId) {
        Product product = productUseCase.findById(productId);
        if (!product.isActive()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }
}
