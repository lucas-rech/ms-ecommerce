package io.github.lucasrech.productservice.utils.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {
    private final String description;
    private final HttpStatus status;

    @Builder
    public BusinessException(String message, String description, HttpStatus status) {
        super(message);
        this.description = description;
        this.status = status;
    }

    public BusinessException(BusinessError error) {
        super(error.getMessage());
        this.description = error.getDescription();
        this.status = error.getStatus();
    }

    public BusinessException(BusinessError error, String description) {
        super(error.getMessage());
        this.description = description;
        this.status = error.getStatus();
    }

}
