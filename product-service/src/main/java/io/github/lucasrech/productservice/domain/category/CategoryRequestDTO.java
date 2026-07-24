package io.github.lucasrech.productservice.domain.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(
        Integer parentCategoryId,

        @NotBlank(message = "Descrição não pode ser nula ou vazia")
        @Size(max = 400, message = "Descrição não pode ter mais de 400 caracteres")
        String description,
        
        Boolean isActive
) {
}
