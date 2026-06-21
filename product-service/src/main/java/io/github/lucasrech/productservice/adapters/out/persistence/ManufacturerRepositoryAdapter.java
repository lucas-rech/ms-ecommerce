package io.github.lucasrech.productservice.adapters.out.persistence;

import io.github.lucasrech.productservice.adapters.out.persistence.entities.JpaManufacturerEntity;
import io.github.lucasrech.productservice.adapters.out.persistence.repositories.JpaManufacturerRepository;
import io.github.lucasrech.productservice.domain.manufacturer.Manufacturer;
import io.github.lucasrech.productservice.domain.manufacturer.ManufacturerRepository;
import io.github.lucasrech.productservice.utils.mappers.ManufacturerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ManufacturerRepositoryAdapter implements ManufacturerRepository {
    private final JpaManufacturerRepository jpaManufacturerRepository;

    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        JpaManufacturerEntity entityToSave = ManufacturerMapper.toEntity(manufacturer);

        JpaManufacturerEntity savedEntity = jpaManufacturerRepository.save(entityToSave);

        return ManufacturerMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Manufacturer> findById(Short id) {
        return jpaManufacturerRepository.findById(id)
                .map(ManufacturerMapper::toDomain);
    }

    @Override
    public Page<Manufacturer> findAll(Pageable pageable) {
        return jpaManufacturerRepository.findAll(pageable)
                .map(ManufacturerMapper::toDomain);
    }

    @Override
    public Optional<Manufacturer> findByCnpj(String cnpj) {
        return jpaManufacturerRepository.findByCnpj(cnpj)
                .map(ManufacturerMapper::toDomain);
    }

    @Override
    public boolean existsByCnpj(String cnpj) {
        return jpaManufacturerRepository.existsByCnpj(cnpj);
    }
}
