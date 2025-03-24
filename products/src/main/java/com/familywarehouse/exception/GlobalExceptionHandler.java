package com.familywarehouse.exception;

import com.familywarehouse.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String defaultMessage = error.getDefaultMessage();
            validationErrors.put(field, defaultMessage);
        });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(validationErrors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(Exception exception,
                                                                            WebRequest webRequest) {
        log.error(exception.toString());
        return generateErrorResponse(exception, webRequest, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                            WebRequest webRequest) {
        return generateErrorResponse(exception, webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductAlreadyExistedException.class)
    public ResponseEntity<ErrorResponseDto> handlerUserAlreadyExistsException(ProductAlreadyExistedException exception,
                                                                              WebRequest webRequest) {
        return generateErrorResponse(exception, webRequest, HttpStatus.BAD_REQUEST);

    }

    private static <T extends Throwable> ResponseEntity<ErrorResponseDto> generateErrorResponse(T exception,
                                                                                                WebRequest webRequest,
                                                                                                HttpStatus status) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                status,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(status)
                .body(errorResponseDto);
    }
}
