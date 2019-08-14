package com.desafio.lanchonete.controller;

import com.desafio.lanchonete.exception.DuplicateKeyException;
import com.desafio.lanchonete.exception.EntityNotFoundException;
import com.desafio.lanchonete.exception.InvalidFieldException;
import com.desafio.lanchonete.model.Error;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    @ExceptionHandler({InvalidFieldException.class, EntityNotFoundException.class, JsonMappingException.class})
    public ResponseEntity<Error> serviceException(Exception ex){
        log.error(ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Error> duplicateJeyException(Exception ex){
        log.error(ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler ({Exception.class, RuntimeException.class})
    public ResponseEntity<Error> defaultException(Exception ex){
        log.error("A wild internal server error appeared!", ex);
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    private ResponseEntity<Error> buildResponseEntity(HttpStatus status, Exception ex){
        Error error = buildError(status.value(), ex);
        return new ResponseEntity<>(error, status);
    }

    private Error buildError(int status, Exception ex){
        return Error.builder().status(status).message(ex.getMessage()).build();
    }
}
