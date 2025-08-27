package com.cronometro.servicio.servicecube.controller;

import com.cronometro.servicio.servicecube.exceptions.BasicNotFoundException;
import com.cronometro.servicio.servicecube.exceptions.IdInvalidException;
import com.cronometro.servicio.servicecube.exceptions.UsernamePasswordException;
import com.cronometro.servicio.servicecube.models.dtos.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ExceptionController {

    private ResponseEntity<ErrorDto> generateError(HttpStatus status, String message) {
        var err = new ErrorDto(status, message);
        return ResponseEntity.status(err.getStatusCode()).body(err);
    }

    @ExceptionHandler({
            IdInvalidException.class,
            UsernamePasswordException.class
    })
    public ResponseEntity<?> badRequest(Exception e){
        return generateError(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({
            BasicNotFoundException.class,
            NoResourceFoundException.class
    })
    public ResponseEntity<?> notfound(Exception e){
        return generateError(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
