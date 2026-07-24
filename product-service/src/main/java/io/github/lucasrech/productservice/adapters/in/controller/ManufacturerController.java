package io.github.lucasrech.productservice.adapters.in.controller;

import io.github.lucasrech.productservice.application.usecases.ManufacturerUseCase;
import io.github.lucasrech.productservice.domain.manufacturer.Manufacturer;
import io.github.lucasrech.productservice.domain.manufacturer.ManufacturerRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;



@RestController
@RequestMapping("/manufacturer")
@RequiredArgsConstructor
public class ManufacturerController {

    private final ManufacturerUseCase manufacturerUseCase;


    @PostMapping
    public ResponseEntity<Void> createManufacturer(@RequestBody @Valid ManufacturerRequestDTO requestDTO) {
        manufacturerUseCase.insert(requestDTO);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{manufacturerId}")
    public ResponseEntity<Void> updateManufacturer(
            @PathVariable Short manufacturerId,
            @RequestBody ManufacturerRequestDTO requestDTO) {

        manufacturerUseCase.update(manufacturerId, requestDTO);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{manufacturerId}")
    public ResponseEntity<Void> deleteManufacturerById(@PathVariable Short manufacturerId) {
        manufacturerUseCase.delete(manufacturerId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<Manufacturer>> findAllManufacturers(
            @PageableDefault(size = 15, sort = "inclusionDate", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(manufacturerUseCase.findAll(pageable));
    }

    @GetMapping("/{manufacturerId}")
    public ResponseEntity<Manufacturer> findManufacturerById(@PathVariable Short manufacturerId) {
        return ResponseEntity.ok(manufacturerUseCase.findById(manufacturerId));
    }

    @GetMapping("/cnpj/{cnpjCode}")
    public ResponseEntity<Manufacturer> findManufacturerByCnpj(@PathVariable String cnpjCode) {
        return ResponseEntity.ok(manufacturerUseCase.findByCnpj(cnpjCode));
    }



}
