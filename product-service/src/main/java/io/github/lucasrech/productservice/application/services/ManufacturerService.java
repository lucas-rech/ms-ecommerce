package io.github.lucasrech.productservice.application.services;

import io.github.lucasrech.productservice.application.usecases.ManufacturerUseCase;
import io.github.lucasrech.productservice.domain.manufacturer.Manufacturer;
import io.github.lucasrech.productservice.domain.manufacturer.ManufacturerRepository;
import io.github.lucasrech.productservice.domain.manufacturer.ManufacturerRequestDTO;
import io.github.lucasrech.productservice.utils.mappers.ManufacturerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManufacturerService implements ManufacturerUseCase {
    private final ManufacturerRepository manufacturerRepository;


    @Override
    public void insert(ManufacturerRequestDTO manufacturerDTO) {
        Manufacturer manufacturer = ManufacturerMapper.dtoToDomain(manufacturerDTO);

        manufacturer.setCompanyName(manufacturer.getCompanyName().toUpperCase());
        manufacturer.setTradeName(manufacturer.getTradeName().toUpperCase());

        manufacturerRepository.save(manufacturer);
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
        return Optional.empty();
    }
}
