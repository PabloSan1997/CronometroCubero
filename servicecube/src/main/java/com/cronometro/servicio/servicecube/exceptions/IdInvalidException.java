package com.cronometro.servicio.servicecube.exceptions;

public class IdInvalidException extends RuntimeException{
    public IdInvalidException() {
    }

    public IdInvalidException(String message) {
        super(message);
    }
}
