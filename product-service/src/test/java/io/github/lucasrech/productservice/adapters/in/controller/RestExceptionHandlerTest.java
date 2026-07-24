package io.github.lucasrech.productservice.adapters.in.controller;

import io.github.lucasrech.productservice.utils.exception.BusinessException;
import io.github.lucasrech.productservice.utils.exception.ErrorResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RestExceptionHandlerTest {

    private final RestExceptionHandler exceptionHandler = new RestExceptionHandler();

    @Test
    @DisplayName("Should handle BusinessException")
    void handleBusinessException() {
        BusinessException ex = BusinessException.builder()
                .message("Test message")
                .description("Test description")
                .status(HttpStatus.BAD_REQUEST)
                .build();
        ex.setStackTrace(new StackTraceElement[]{new StackTraceElement("Class", "method", "file", 1)});

        ResponseEntity<ErrorResponseDTO> response = exceptionHandler.handleBusinessException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Test message", response.getBody().message());
    }

    @Test
    @DisplayName("Should handle MethodArgumentNotValidException")
    void handleValidationException() {
        MethodParameter parameter = mock(MethodParameter.class);
        BindingResult bindingResult = mock(BindingResult.class);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter, bindingResult);

        FieldError fieldError = new FieldError("object", "field", "must not be null");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        ResponseEntity<ErrorResponseDTO> response = exceptionHandler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro de Validação", response.getBody().message());
    }
}
