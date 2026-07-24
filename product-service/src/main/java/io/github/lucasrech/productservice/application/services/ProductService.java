package io.github.lucasrech.productservice.application.services;

import io.github.lucasrech.productservice.application.usecases.ProductUseCase;
import io.github.lucasrech.productservice.domain.category.Category;
import io.github.lucasrech.productservice.domain.category.CategoryRepository;
import io.github.lucasrech.productservice.domain.manufacturer.Manufacturer;
import io.github.lucasrech.productservice.domain.manufacturer.ManufacturerRepository;
import io.github.lucasrech.productservice.domain.product.Product;
import io.github.lucasrech.productservice.domain.product.ProductRepository;
import io.github.lucasrech.productservice.domain.product.ProductRequestDTO;
import io.github.lucasrech.productservice.utils.exception.BusinessError;
import io.github.lucasrech.productservice.utils.exception.BusinessException;
import io.github.lucasrech.productservice.utils.mappers.ProductMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {

    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void insert(ProductRequestDTO productDTO) {
        if (productDTO == null) {
            throw BusinessError.REQUEST_NULLABLE_OBJECT.asException();
        }

        validateUniqueCodes(productDTO.cdEan(), productDTO.cdSku(), null);

        Product product = ProductMapper.dtoToDomain(productDTO);
        
        assignManufacturer(productDTO.manufacturerId(), product);
        assignCategories(productDTO.categoryIds(), product);

        productRepository.save(product);
        log.info("Produto inserido com sucesso: {}", productDTO.name());
    }

    @Override
    @Transactional
    public void update(Long id, ProductRequestDTO productDTO) {
        if (id == null || productDTO == null) {
            throw BusinessError.REQUEST_NULLABLE_OBJECT.asException();
        }

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> BusinessException.builder()
                        .message("Produto não encontrado")
                        .description("O produto informado para atualização não existe")
                        .status(HttpStatus.NOT_FOUND)
                        .build());

        validateUniqueCodes(productDTO.cdEan(), productDTO.cdSku(), existingProduct);

        existingProduct.setName(productDTO.name());
        existingProduct.setDescription(productDTO.description());
        existingProduct.setEanCode(productDTO.cdEan());
        existingProduct.setSkuCode(productDTO.cdSku());
        existingProduct.setPrice(productDTO.price());

        if (productDTO.isActive() != null) {
            existingProduct.setActive(productDTO.isActive());
        }

        assignManufacturer(productDTO.manufacturerId(), existingProduct);
        assignCategories(productDTO.categoryIds(), existingProduct);

        productRepository.save(existingProduct);
        log.info("Produto atualizado com sucesso: {}", id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw BusinessError.REQUEST_NULLABLE_OBJECT.asException();
        }

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> BusinessException.builder()
                        .message("Produto não encontrado")
                        .description("O produto informado para deleção não existe")
                        .status(HttpStatus.NOT_FOUND)
                        .build());

        existingProduct.setActive(false);
        productRepository.save(existingProduct);
        log.info("Produto inativado (soft delete) com sucesso: {}", id);
    }

    @Override
    public Product findById(Long id) {
        if (id == null) {
            throw BusinessError.REQUEST_NULLABLE_OBJECT.asException();
        }

        Product domain = productRepository.findById(id)
                .orElseThrow(() -> BusinessException.builder()
                        .message("Produto não encontrado")
                        .description("O produto informado não existe")
                        .status(HttpStatus.NOT_FOUND)
                        .build());

        log.info("Produto encontrado e retornado com sucesso: {}", id);
        return domain;
    }

    @Override
    public Page<Product> findAll(Pageable pageable, Boolean active) {
        log.info("Listagem paginada de produtos solicitada");
        
        if (active != null) {
            return productRepository.findAllByIsActive(active, pageable);
        }
        
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findAllByManufacturer(Short manufacturerId, Pageable pageable) {
        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> BusinessError.MANUFACTURER_NOT_FOUND.asException(String.valueOf(manufacturerId)));
                
        return productRepository.findAllByManufacturer(manufacturer, pageable);
    }
    
    private void assignManufacturer(Short manufacturerId, Product product) {
        if (manufacturerId != null) {
            Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId)
                    .orElseThrow(() -> BusinessException.builder()
                            .message("Fabricante não encontrado")
                            .description("O fabricante informado não existe")
                            .status(HttpStatus.NOT_FOUND)
                            .build());
            
            if (!manufacturer.isActive()) {
                throw BusinessException.builder()
                        .message("Fabricante inativo")
                        .description("Não é possível associar a um fabricante inativo")
                        .status(HttpStatus.BAD_REQUEST)
                        .build();
            }
            product.setManufacturer(manufacturer);
        }
    }
    
    private void assignCategories(List<Integer> categoryIds, Product product) {
        if (categoryIds != null && !categoryIds.isEmpty()) {
            List<Category> categories = new ArrayList<>();
            for (Integer catId : categoryIds) {
                Category cat = categoryRepository.findById(catId)
                        .orElseThrow(() -> BusinessException.builder()
                                .message("Categoria não encontrada")
                                .description("A categoria informada não existe: " + catId)
                                .status(HttpStatus.NOT_FOUND)
                                .build());
                
                if (!cat.isActive()) {
                    throw BusinessException.builder()
                            .message("Categoria inativa")
                            .description("Não é possível associar a uma categoria inativa: " + catId)
                            .status(HttpStatus.BAD_REQUEST)
                            .build();
                }
                categories.add(cat);
            }
            product.setCategories(categories);
        } else {
            product.setCategories(new ArrayList<>());
        }
    }
    
    private void validateUniqueCodes(String ean, String sku, Product existingProduct) {
        if (existingProduct != null) {
            if (!existingProduct.getEanCode().equals(ean) && productRepository.existsByEanCode(ean)) {
                throw BusinessException.builder()
                        .message("EAN em uso")
                        .description("Já existe um produto com este EAN")
                        .status(HttpStatus.CONFLICT)
                        .build();
            }
            if (!existingProduct.getSkuCode().equals(sku) && productRepository.existsBySkuCode(sku)) {
                throw BusinessException.builder()
                        .message("SKU em uso")
                        .description("Já existe um produto com este SKU")
                        .status(HttpStatus.CONFLICT)
                        .build();
            }
        } else {
            if (productRepository.existsByEanCode(ean)) {
                throw BusinessException.builder()
                        .message("EAN em uso")
                        .description("Já existe um produto com este EAN")
                        .status(HttpStatus.CONFLICT)
                        .build();
            }
            if (productRepository.existsBySkuCode(sku)) {
                throw BusinessException.builder()
                        .message("SKU em uso")
                        .description("Já existe um produto com este SKU")
                        .status(HttpStatus.CONFLICT)
                        .build();
            }
        }
    }
}
