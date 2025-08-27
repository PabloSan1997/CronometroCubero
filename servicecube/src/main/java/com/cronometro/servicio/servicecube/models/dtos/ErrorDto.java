package com.cronometro.servicio.servicecube.models.dtos;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ErrorDto {
    private String error;
    private Integer statusCode;
    private String message;
    private Date timestamp;
    public ErrorDto(){}
    public ErrorDto(HttpStatus status, String message){
        this.error = status.getReasonPhrase();
        this.message = message;
        this.timestamp = new Date();
        this.statusCode = status.value();
    }
}
