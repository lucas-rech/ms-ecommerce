package io.github.lucasrech.productservice.adapters.in.controller;

import io.github.lucasrech.productservice.application.usecases.ManufacturerUseCase;
import io.github.lucasrech.productservice.domain.manufacturer.ManufacturerRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manufacturer")
@RequiredArgsConstructor
public class ManufacturerController {

    private final ManufacturerUseCase manufacturerUseCase;


    @PostMapping
    public ResponseEntity<Void> createManufacturer(@RequestBody ManufacturerRequestDTO requestDTO) {
        manufacturerUseCase.insert(requestDTO);

        return ResponseEntity.noContent().build();
    }

}
