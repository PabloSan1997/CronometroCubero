package com.cronometro.servicio.servicecube.exceptions;

public class BasicNotFoundException extends RuntimeException{
    public BasicNotFoundException() {
    }

    public BasicNotFoundException(String message) {
        super(message);
    }
}
