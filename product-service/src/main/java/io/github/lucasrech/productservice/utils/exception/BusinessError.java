package io.github.lucasrech.productservice.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BusinessError {
    REQUEST_NULLABLE_OBJECT("Objeto inválido", "O objeto enviado é nulo e não pôde ser processado", HttpStatus.BAD_REQUEST);

    private final String message;
    private final String description;
    private final HttpStatus status;


    public BusinessException asException() {
        return new BusinessException(this);
    }
}
