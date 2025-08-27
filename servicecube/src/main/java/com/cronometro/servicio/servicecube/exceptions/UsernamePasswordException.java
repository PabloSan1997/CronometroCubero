package com.cronometro.servicio.servicecube.exceptions;

public class UsernamePasswordException extends RuntimeException{
    public UsernamePasswordException() {
    }

    public UsernamePasswordException(String message) {
        super(message);
    }
}
