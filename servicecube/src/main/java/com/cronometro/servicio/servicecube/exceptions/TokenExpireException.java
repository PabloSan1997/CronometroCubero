package com.cronometro.servicio.servicecube.exceptions;

public class TokenExpireException extends RuntimeException{
    public TokenExpireException() {
        super("expiro");
    }
}
