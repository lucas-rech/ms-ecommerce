package io.github.lucasrech.productservice.domain.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record ProductRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 250, message = "O nome deve ter no máximo 250 caracteres")
        String name,

        @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres")
        String description,

        @NotBlank(message = "O EAN é obrigatório")
        @Size(max = 14, message = "O EAN deve ter no máximo 14 caracteres")
        String cdEan,

        @NotBlank(message = "O SKU é obrigatório")
        @Size(max = 50, message = "O SKU deve ter no máximo 50 caracteres")
        String cdSku,

        @NotNull(message = "O preço é obrigatório")
        @PositiveOrZero(message = "O preço deve ser maior ou igual a zero")
        BigDecimal price,

        @NotNull(message = "O ID do fabricante é obrigatório")
        Short manufacturerId,

        List<Integer> categoryIds,

        Boolean isActive
) {
}
