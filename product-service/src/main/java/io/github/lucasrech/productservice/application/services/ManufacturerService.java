package io.github.lucasrech.productservice.application.services;

import io.github.lucasrech.productservice.application.usecases.ManufacturerUseCase;
import io.github.lucasrech.productservice.domain.manufacturer.Manufacturer;
import io.github.lucasrech.productservice.domain.manufacturer.ManufacturerRepository;
import io.github.lucasrech.productservice.domain.manufacturer.ManufacturerRequestDTO;
import io.github.lucasrech.productservice.utils.exception.BusinessError;
import io.github.lucasrech.productservice.utils.exception.BusinessException;
import io.github.lucasrech.productservice.utils.mappers.ManufacturerMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManufacturerService implements ManufacturerUseCase {

    private final ManufacturerRepository manufacturerRepository;

    @Override
    @Transactional
    public void insert(ManufacturerRequestDTO manufacturerDTO) {
        if (manufacturerDTO == null) {
            throw BusinessError.REQUEST_NULLABLE_OBJECT.asException();
        }

        if (manufacturerRepository.existsByCnpj(manufacturerDTO.cnpj())) {
            throw BusinessException.builder()
                    .message("Fabricante já existe")
                    .description("Já existe um fabricante com esse CNPJ")
                    .status(HttpStatus.CONFLICT)
                    .build();
        }

        Manufacturer manufacturer = ManufacturerMapper.dtoToDomain(manufacturerDTO);

        manufacturer.setCompanyName(manufacturer.getCompanyName().toUpperCase());
        manufacturer.setTradeName(manufacturer.getTradeName().toUpperCase());

        manufacturerRepository.save(manufacturer);
        log.info("Fabricante adicionado com sucesso com o CNPJ {}", manufacturer.getCnpj());
    }

    @Override
    @Transactional
    public void update(Short id, ManufacturerRequestDTO manufacturerDTO) {
        if (id == null || manufacturerDTO == null) {
            throw BusinessError.REQUEST_NULLABLE_OBJECT.asException();
        }

        Manufacturer existingManufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> BusinessError.MANUFACTURER_NOT_FOUND.asException(String.valueOf(id)));

        if (!existingManufacturer.getCnpj().equals(manufacturerDTO.cnpj()) &&
                manufacturerRepository.existsByCnpj(manufacturerDTO.cnpj())) {

            throw BusinessException.builder()
                    .message("Conflito de CNPJ")
                    .description("Já existe outro fabricante cadastrado com este CNPJ")
                    .status(HttpStatus.CONFLICT)
                    .build();
        }

        existingManufacturer.setCompanyName(manufacturerDTO.companyName().toUpperCase());
        existingManufacturer.setTradeName(manufacturerDTO.tradeName().toUpperCase());
        existingManufacturer.setCnpj(manufacturerDTO.cnpj());

        manufacturerRepository.save(existingManufacturer);
        log.info("Fabricante atualizado com sucesso: {}", existingManufacturer.getId());
    }

    @Override
    @Transactional
    public void delete(Short id) {
        if (id == null) {
            throw BusinessError.REQUEST_NULLABLE_OBJECT.asException();
        }

        Manufacturer existingManufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> BusinessError.MANUFACTURER_NOT_FOUND.asException(String.valueOf(id)));

        existingManufacturer.setActive(false);
        manufacturerRepository.save(existingManufacturer);
        log.info("Fabricante inativado (soft delete) com sucesso: {}", id);
    }

    @Override
    public Manufacturer findById(Short id) {
        if (id == null) {
            throw BusinessError.REQUEST_NULLABLE_OBJECT.asException();
        }

        Manufacturer domain = manufacturerRepository.findById(id)
                .orElseThrow(() -> BusinessError.MANUFACTURER_NOT_FOUND.asException(String.valueOf(id)));

        log.info("Fabricante encontrado e retornado com sucesso: {}", domain.getId());
        return domain;
    }

    @Override
    public Page<Manufacturer> findAll(Pageable pageable) {
        log.info("Listagem paginada de fabricantes solicitada");
        return manufacturerRepository.findAll(pageable);
    }

    @Override
    public Manufacturer findByCnpj(String cnpj) {
        if (cnpj == null || cnpj.isBlank()) {
            throw BusinessError.REQUEST_NULLABLE_OBJECT.asException();
        }

        Manufacturer domain = manufacturerRepository.findByCnpj(cnpj)
                .orElseThrow(() -> BusinessError.MANUFACTURER_NOT_FOUND.asException(cnpj));

        log.info("Fabricante encontrado por CNPJ e retornado com sucesso: {}", domain.getId());
        return domain;
    }
}