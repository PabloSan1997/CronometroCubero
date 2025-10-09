package com.cronometro.servicio.servicecube.controller;

import com.cronometro.servicio.servicecube.exceptions.*;
import com.cronometro.servicio.servicecube.models.dtos.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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
            UsernamePasswordException.class,
            SolvesBadRequestException.class
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

    @ExceptionHandler({
            AuthJwtException.class,
            TokenExpireException.class
    })
    public ResponseEntity<?> unauthorized(Exception e){
        return generateError(HttpStatus.UNAUTHORIZED, e.getMessage());
    }
    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<?> refreshToken(RefreshTokenException e){
        ResponseCookie cookie = ResponseCookie.from("mitoken", "")
                .httpOnly(true)
                .sameSite("Lax")
                .secure(false) //https true
                .maxAge(  0)
                .path("/")
                .build();
        ErrorDto errorDto = new ErrorDto(HttpStatus.UNAUTHORIZED, e.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.status(errorDto.getStatusCode()).headers(headers).body(errorDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> notholainformation(Exception e){
        return generateError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
