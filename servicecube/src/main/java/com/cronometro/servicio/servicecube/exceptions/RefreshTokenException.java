package com.cronometro.servicio.servicecube.exceptions;

public class RefreshTokenException extends RuntimeException{
    public RefreshTokenException() {
        super("vuelve a iniciar seccion");
    }
}
