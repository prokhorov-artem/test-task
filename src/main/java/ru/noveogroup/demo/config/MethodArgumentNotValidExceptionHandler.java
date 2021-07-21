package ru.noveogroup.demo.config;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<CustomError> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<CustomError> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errors.add(CustomError.builder()
                .errorMessage(error.getDefaultMessage())
                .fieldName(((FieldError) error).getField())
                .rejectedValue(((FieldError) error).getRejectedValue())
                .build());
        });
        return errors;
    }

    @Data
    @Builder
    private static class CustomError {
        private String errorMessage;
        private Object rejectedValue;
        private String fieldName;
    }


}