package io.github.lucasrech.productservice.application.services;

import io.github.lucasrech.productservice.domain.manufacturer.Manufacturer;
import io.github.lucasrech.productservice.domain.manufacturer.ManufacturerRepository;
import io.github.lucasrech.productservice.domain.manufacturer.ManufacturerRequestDTO;
import io.github.lucasrech.productservice.utils.exception.BusinessError;
import io.github.lucasrech.productservice.utils.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManufacturerServiceTest {

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @InjectMocks
    private ManufacturerService manufacturerService;

    @Captor
    private ArgumentCaptor<Manufacturer> manufacturerCaptor;

    @Test
    @DisplayName("Should successfully insert a new manufacturer")
    void insert_Success() {
        ManufacturerRequestDTO dto = new ManufacturerRequestDTO("Trade Name", "Company Name", "12345678901234", true);
        when(manufacturerRepository.existsByCnpj(dto.cnpj())).thenReturn(false);

        manufacturerService.insert(dto);

        verify(manufacturerRepository).save(manufacturerCaptor.capture());
        Manufacturer savedManufacturer = manufacturerCaptor.getValue();

        assertEquals("COMPANY NAME", savedManufacturer.getCompanyName());
        assertEquals("TRADE NAME", savedManufacturer.getTradeName());
        assertEquals("12345678901234", savedManufacturer.getCnpj());
    }

    @Test
    @DisplayName("Should throw exception when inserting null dto")
    void insert_NullDto_ThrowsException() {
        BusinessException exception = assertThrows(BusinessException.class, () -> manufacturerService.insert(null));
        assertEquals(BusinessError.REQUEST_NULLABLE_OBJECT.getMessage(), exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(manufacturerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when inserting with existing CNPJ")
    void insert_CnpjExists_ThrowsException() {
        ManufacturerRequestDTO dto = new ManufacturerRequestDTO("Trade Name", "Company Name", "12345678901234", true);
        when(manufacturerRepository.existsByCnpj(dto.cnpj())).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () -> manufacturerService.insert(dto));
        assertEquals("Fabricante já existe", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        verify(manufacturerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should successfully update a manufacturer")
    void update_Success() {
        Short id = 1;
        ManufacturerRequestDTO dto = new ManufacturerRequestDTO("New Trade", "New Company", "98765432109876", true);
        
        Manufacturer existingManufacturer = new Manufacturer();
        existingManufacturer.setId(id);
        existingManufacturer.setCnpj("12345678901234");
        
        when(manufacturerRepository.findById(id)).thenReturn(Optional.of(existingManufacturer));
        when(manufacturerRepository.existsByCnpj(dto.cnpj())).thenReturn(false);

        manufacturerService.update(id, dto);

        verify(manufacturerRepository).save(manufacturerCaptor.capture());
        Manufacturer updatedManufacturer = manufacturerCaptor.getValue();

        assertEquals("NEW COMPANY", updatedManufacturer.getCompanyName());
        assertEquals("NEW TRADE", updatedManufacturer.getTradeName());
        assertEquals("98765432109876", updatedManufacturer.getCnpj());
    }

    @Test
    @DisplayName("Should update when CNPJ is the same")
    void update_SameCnpj_Success() {
        Short id = 1;
        ManufacturerRequestDTO dto = new ManufacturerRequestDTO("New Trade", "New Company", "12345678901234", true);
        
        Manufacturer existingManufacturer = new Manufacturer();
        existingManufacturer.setId(id);
        existingManufacturer.setCnpj("12345678901234");
        
        when(manufacturerRepository.findById(id)).thenReturn(Optional.of(existingManufacturer));

        manufacturerService.update(id, dto);

        verify(manufacturerRepository).save(any(Manufacturer.class));
        verify(manufacturerRepository, never()).existsByCnpj(anyString());
    }

    @Test
    @DisplayName("Should throw exception when updating with null id or dto")
    void update_NullIdOrDto_ThrowsException() {
        assertThrows(BusinessException.class, () -> manufacturerService.update(null, new ManufacturerRequestDTO("T", "C", "C", true)));
        assertThrows(BusinessException.class, () -> manufacturerService.update((short) 1, null));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent manufacturer")
    void update_ManufacturerNotFound_ThrowsException() {
        Short id = 1;
        ManufacturerRequestDTO dto = new ManufacturerRequestDTO("T", "C", "C", true);
        when(manufacturerRepository.findById(id)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> manufacturerService.update(id, dto));
        assertEquals(BusinessError.MANUFACTURER_NOT_FOUND.getMessage(), exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    @DisplayName("Should throw exception when updating CNPJ to one that already exists")
    void update_CnpjConflict_ThrowsException() {
        Short id = 1;
        ManufacturerRequestDTO dto = new ManufacturerRequestDTO("New Trade", "New Company", "98765432109876", true);
        
        Manufacturer existingManufacturer = new Manufacturer();
        existingManufacturer.setId(id);
        existingManufacturer.setCnpj("12345678901234");
        
        when(manufacturerRepository.findById(id)).thenReturn(Optional.of(existingManufacturer));
        when(manufacturerRepository.existsByCnpj(dto.cnpj())).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () -> manufacturerService.update(id, dto));
        assertEquals("Conflito de CNPJ", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        verify(manufacturerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should successfully delete a manufacturer")
    void delete_Success() {
        Short id = 1;
        Manufacturer existingManufacturer = new Manufacturer();
        existingManufacturer.setId(id);
        when(manufacturerRepository.findById(id)).thenReturn(Optional.of(existingManufacturer));

        manufacturerService.delete(id);

        verify(manufacturerRepository).save(any(Manufacturer.class));
        assertFalse(existingManufacturer.isActive());
    }

    @Test
    @DisplayName("Should throw exception when deleting with null id")
    void delete_NullId_ThrowsException() {
        assertThrows(BusinessException.class, () -> manufacturerService.delete(null));
        verify(manufacturerRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent manufacturer")
    void delete_ManufacturerNotFound_ThrowsException() {
        Short id = 1;
        when(manufacturerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> manufacturerService.delete(id));
        verify(manufacturerRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Should return manufacturer by id")
    void findById_Success() {
        Short id = 1;
        Manufacturer expectedManufacturer = new Manufacturer();
        expectedManufacturer.setId(id);
        when(manufacturerRepository.findById(id)).thenReturn(Optional.of(expectedManufacturer));

        Manufacturer result = manufacturerService.findById(id);

        assertEquals(expectedManufacturer, result);
    }

    @Test
    @DisplayName("Should throw exception when finding by null id")
    void findById_NullId_ThrowsException() {
        assertThrows(BusinessException.class, () -> manufacturerService.findById(null));
    }

    @Test
    @DisplayName("Should throw exception when finding non-existent manufacturer by id")
    void findById_ManufacturerNotFound_ThrowsException() {
        Short id = 1;
        when(manufacturerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> manufacturerService.findById(id));
    }

    @Test
    @DisplayName("Should return manufacturer by CNPJ")
    void findByCnpj_Success() {
        String cnpj = "12345678901234";
        Manufacturer expectedManufacturer = new Manufacturer();
        expectedManufacturer.setCnpj(cnpj);
        when(manufacturerRepository.findByCnpj(cnpj)).thenReturn(Optional.of(expectedManufacturer));

        Manufacturer result = manufacturerService.findByCnpj(cnpj);

        assertEquals(expectedManufacturer, result);
    }

    @Test
    @DisplayName("Should throw exception when finding by null or blank CNPJ")
    void findByCnpj_NullOrBlank_ThrowsException() {
        assertThrows(BusinessException.class, () -> manufacturerService.findByCnpj(null));
        assertThrows(BusinessException.class, () -> manufacturerService.findByCnpj("   "));
    }

    @Test
    @DisplayName("Should throw exception when finding non-existent manufacturer by CNPJ")
    void findByCnpj_ManufacturerNotFound_ThrowsException() {
        String cnpj = "12345678901234";
        when(manufacturerRepository.findByCnpj(cnpj)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> manufacturerService.findByCnpj(cnpj));
    }

    @Test
    @DisplayName("Should return a page of manufacturers")
    void findAll_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Manufacturer manufacturer = new Manufacturer();
        Page<Manufacturer> expectedPage = new PageImpl<>(List.of(manufacturer));
        
        when(manufacturerRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<Manufacturer> result = manufacturerService.findAll(pageable);

        assertEquals(expectedPage, result);
        assertEquals(1, result.getTotalElements());
    }
}
