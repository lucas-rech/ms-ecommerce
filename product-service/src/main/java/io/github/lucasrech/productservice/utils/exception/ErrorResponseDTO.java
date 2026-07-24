package io.github.lucasrech.productservice.utils.exception;

import lombok.Builder;

@Builder
public record ErrorResponseDTO(
        String message,
        String description
) {
}
