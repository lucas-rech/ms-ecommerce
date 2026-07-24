package io.github.lucasrech.productservice.adapters.out.persistence;

import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaManufacturerEntity;
import io.github.lucasrech.productservice.adapters.out.persistence.repositories.JpaManufacturerRepository;
import io.github.lucasrech.productservice.domain.manufacturer.Manufacturer;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManufacturerRepositoryAdapterTest {

    @Mock
    private JpaManufacturerRepository jpaManufacturerRepository;

    @InjectMocks
    private ManufacturerRepositoryAdapter adapter;

    @Captor
    private ArgumentCaptor<JpaManufacturerEntity> entityCaptor;

    @Test
    @DisplayName("Should successfully save a manufacturer")
    void save_Success() {
        Manufacturer domain = new Manufacturer();
        domain.setTradeName("Trade");
        domain.setCompanyName("Company");
        domain.setCnpj("12345678901234");

        JpaManufacturerEntity savedEntity = new JpaManufacturerEntity();
        savedEntity.setId((short) 1);
        savedEntity.setTradeName("Trade");
        savedEntity.setCompanyName("Company");
        savedEntity.setCnpj("12345678901234");

        when(jpaManufacturerRepository.save(any(JpaManufacturerEntity.class))).thenReturn(savedEntity);

        Manufacturer result = adapter.save(domain);

        verify(jpaManufacturerRepository).save(entityCaptor.capture());
        JpaManufacturerEntity capturedEntity = entityCaptor.getValue();

        assertEquals(domain.getTradeName(), capturedEntity.getTradeName());
        assertEquals(domain.getCompanyName(), capturedEntity.getCompanyName());
        assertEquals(domain.getCnpj(), capturedEntity.getCnpj());

        assertNotNull(result);
        assertEquals((short) 1, result.getId());
        assertEquals("Trade", result.getTradeName());
    }

    @Test
    @DisplayName("Should find a manufacturer by id")
    void findById_Success() {
        Short id = 1;
        JpaManufacturerEntity entity = new JpaManufacturerEntity();
        entity.setId(id);
        
        when(jpaManufacturerRepository.findById(id)).thenReturn(Optional.of(entity));

        Optional<Manufacturer> result = adapter.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    @DisplayName("Should return empty when manufacturer id is not found")
    void findById_NotFound() {
        Short id = 1;
        when(jpaManufacturerRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Manufacturer> result = adapter.findById(id);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should return a page of manufacturers")
    void findAll_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        JpaManufacturerEntity entity = new JpaManufacturerEntity();
        Page<JpaManufacturerEntity> page = new PageImpl<>(List.of(entity));

        when(jpaManufacturerRepository.findAll(pageable)).thenReturn(page);

        Page<Manufacturer> result = adapter.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("Should find a manufacturer by CNPJ")
    void findByCnpj_Success() {
        String cnpj = "12345678901234";
        JpaManufacturerEntity entity = new JpaManufacturerEntity();
        entity.setCnpj(cnpj);

        when(jpaManufacturerRepository.findByCnpj(cnpj)).thenReturn(Optional.of(entity));

        Optional<Manufacturer> result = adapter.findByCnpj(cnpj);

        assertTrue(result.isPresent());
        assertEquals(cnpj, result.get().getCnpj());
    }

    @Test
    @DisplayName("Should return empty when manufacturer CNPJ is not found")
    void findByCnpj_NotFound() {
        String cnpj = "12345678901234";
        when(jpaManufacturerRepository.findByCnpj(cnpj)).thenReturn(Optional.empty());

        Optional<Manufacturer> result = adapter.findByCnpj(cnpj);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should return true when CNPJ exists")
    void existsByCnpj_True() {
        String cnpj = "12345678901234";
        when(jpaManufacturerRepository.existsByCnpj(cnpj)).thenReturn(true);

        boolean result = adapter.existsByCnpj(cnpj);

        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false when CNPJ does not exist")
    void existsByCnpj_False() {
        String cnpj = "12345678901234";
        when(jpaManufacturerRepository.existsByCnpj(cnpj)).thenReturn(false);

        boolean result = adapter.existsByCnpj(cnpj);

        assertFalse(result);
    }

    @Test
    @DisplayName("Should delete a manufacturer")
    void delete_Success() {
        Manufacturer domain = new Manufacturer();
        domain.setId((short) 1);

        adapter.delete(domain);

        verify(jpaManufacturerRepository).delete(entityCaptor.capture());
        assertEquals((short) 1, entityCaptor.getValue().getId());
    }
}
