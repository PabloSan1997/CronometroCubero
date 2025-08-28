package com.cronometro.servicio.servicecube.exceptions;

public class SolvesBadRequestException extends RuntimeException{
    public SolvesBadRequestException(){
        super("Solo pueden procesarse 5 solves por solicitud");
    }
}
