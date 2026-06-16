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
    public void update(Integer id, ManufacturerRequestDTO manufacturerDTO) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Manufacturer findById(Integer id) {
        if (id == null) {
            throw BusinessError.REQUEST_NULLABLE_OBJECT.asException();
        }

        Manufacturer domain = manufacturerRepository.findById(id)
                .orElseThrow(() -> BusinessError.MANUFACTURER_NOT_FOUND.asException(String.valueOf(id)));

        log.info("Fabricante encontrado e retornado com sucesso {}", domain.getId());
        return domain;
    }

    @Override
    public Page<Manufacturer> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Manufacturer findByCnpj(String cnpj) {
        if (cnpj.isEmpty()) {
            throw BusinessError.REQUEST_NULLABLE_OBJECT.asException();
        }

        Manufacturer domain = manufacturerRepository.findByCnpj(cnpj)
                .orElseThrow(() -> BusinessError.MANUFACTURER_NOT_FOUND.asException(cnpj));

        log.info("Fabricante encontrado e retornado com sucesso {}", domain.getId());
        return domain;
    }
}
