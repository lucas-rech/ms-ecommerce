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

import java.util.Optional;

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

       Optional<Manufacturer> exists = this.findByCnpj(manufacturerDTO.cnpj());
       if (exists.isPresent()) {
           throw BusinessException.builder()
                   .message("Falha ao inserir registro")
                   .description(String.format("Já existe uma filial com esse CNPJ: %s", manufacturerDTO.cnpj()))
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
    public Manufacturer finById(Integer id) {
        return null;
    }

    @Override
    public Page<Manufacturer> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Manufacturer> findByCnpj(String cnpj) {
        if (cnpj.isEmpty()) {
            throw BusinessError.REQUEST_NULLABLE_OBJECT.asException();
        }

        return manufacturerRepository.findByCnpj(cnpj);
    }
}
