package io.github.lucasrech.productservice.adapters.in.controller;

import io.github.lucasrech.productservice.application.usecases.ManufacturerUseCase;
import io.github.lucasrech.productservice.domain.manufacturer.Manufacturer;
import io.github.lucasrech.productservice.domain.manufacturer.ManufacturerRequestDTO;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManufacturerControllerTest {

    @Mock
    private ManufacturerUseCase manufacturerUseCase;

    @InjectMocks
    private ManufacturerController manufacturerController;

    @Test
    @DisplayName("Should return 204 No Content when creating a valid manufacturer")
    void createManufacturer_Success() {
        ManufacturerRequestDTO dto = new ManufacturerRequestDTO("Trade Name", "Company Name", "12345678901234", true);

        doNothing().when(manufacturerUseCase).insert(any(ManufacturerRequestDTO.class));

        ResponseEntity<Void> response = manufacturerController.createManufacturer(dto);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(manufacturerUseCase).insert(dto);
    }

    @Test
    @DisplayName("Should return 204 No Content when updating a manufacturer")
    void updateManufacturer_Success() {
        Short id = 1;
        ManufacturerRequestDTO dto = new ManufacturerRequestDTO("Trade Name", "Company Name", "12345678901234", true);

        doNothing().when(manufacturerUseCase).update(id, dto);

        ResponseEntity<Void> response = manufacturerController.updateManufacturer(id, dto);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(manufacturerUseCase).update(id, dto);
    }

    @Test
    @DisplayName("Should return 204 No Content when deleting a manufacturer")
    void deleteManufacturerById_Success() {
        Short id = 1;

        doNothing().when(manufacturerUseCase).delete(id);

        ResponseEntity<Void> response = manufacturerController.deleteManufacturerById(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(manufacturerUseCase).delete(id);
    }

    @Test
    @DisplayName("Should return 200 OK and page of manufacturers")
    void findAllManufacturers_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Manufacturer manufacturer = new Manufacturer();
        Page<Manufacturer> page = new PageImpl<>(List.of(manufacturer));

        when(manufacturerUseCase.findAll(pageable)).thenReturn(page);

        ResponseEntity<Page<Manufacturer>> response = manufacturerController.findAllManufacturers(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
        verify(manufacturerUseCase).findAll(pageable);
    }

    @Test
    @DisplayName("Should return 200 OK and the manufacturer when finding by ID")
    void findManufacturerById_Success() {
        Short id = 1;
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);

        when(manufacturerUseCase.findById(id)).thenReturn(manufacturer);

        ResponseEntity<Manufacturer> response = manufacturerController.findManufacturerById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(manufacturer, response.getBody());
        verify(manufacturerUseCase).findById(id);
    }

    @Test
    @DisplayName("Should return 200 OK and the manufacturer when finding by CNPJ")
    void findManufacturerByCnpj_Success() {
        String cnpj = "12345678901234";
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCnpj(cnpj);

        when(manufacturerUseCase.findByCnpj(cnpj)).thenReturn(manufacturer);

        ResponseEntity<Manufacturer> response = manufacturerController.findManufacturerByCnpj(cnpj);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(manufacturer, response.getBody());
        verify(manufacturerUseCase).findByCnpj(cnpj);
    }
}
