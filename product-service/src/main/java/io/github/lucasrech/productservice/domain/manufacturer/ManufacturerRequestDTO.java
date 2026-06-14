package io.github.lucasrech.productservice.domain.manufacturer;

public record ManufacturerRequestDTO(
        String tradeName,
        String companyName,
        String cnpj
) {
}
