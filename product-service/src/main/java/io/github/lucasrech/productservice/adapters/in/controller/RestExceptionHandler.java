package io.github.lucasrech.productservice.adapters.in.controller;

import io.github.lucasrech.productservice.utils.exception.BusinessException;
import io.github.lucasrech.productservice.utils.exception.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDTO> handleBusinessException(BusinessException ex) {
        StackTraceElement origin = Arrays.stream(ex.getStackTrace())
                .filter(e -> !e.getClassName().contains("BusinessException"))
                .findFirst()
                .orElse(ex.getStackTrace()[0]);

        String fullClassName = origin.getClassName();
        String simpleClassName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);

        log.warn("[{}:{}] Regra de negócio violada: {}", simpleClassName, origin.getLineNumber(), ex.getDescription());

       ErrorResponseDTO response = ErrorResponseDTO.builder()
               .message(ex.getMessage())
               .description(ex.getDescription())
               .build();

        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .message("Erro de Validação")
                .description(errorMessage)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
