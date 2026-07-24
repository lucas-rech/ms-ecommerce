package io.github.lucasrech.productservice.domain.manufacturer;

import jakarta.validation.constraints.NotBlank;

public record ManufacturerRequestDTO(
        @NotBlank(message = "tradeName não pode ser nulo")
        String tradeName,

        @NotBlank(message = "companyName não pode ser nulo")
        String companyName,

        @NotBlank(message = "cnpj não pode ser nulo")
        String cnpj,
        
        Boolean isActive
) {
}
