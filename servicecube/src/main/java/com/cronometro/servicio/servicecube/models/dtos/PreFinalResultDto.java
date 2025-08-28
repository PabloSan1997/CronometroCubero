package com.cronometro.servicio.servicecube.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PreFinalResultDto {
    private Double max;
    private Double min;
    private Double media;
    private Double avg5;
}
