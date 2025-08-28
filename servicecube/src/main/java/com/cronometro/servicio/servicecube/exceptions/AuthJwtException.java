package com.cronometro.servicio.servicecube.exceptions;

public class AuthJwtException extends RuntimeException{
    public AuthJwtException() {
    }

    public AuthJwtException(String message) {
        super(message);
    }
}
