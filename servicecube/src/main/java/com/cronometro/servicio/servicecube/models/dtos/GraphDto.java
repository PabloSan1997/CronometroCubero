package com.cronometro.servicio.servicecube.models.dtos;

import com.cronometro.servicio.servicecube.models.enitties.FinalResutls;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GraphDto {
    private Date createdAt;
    private Double avg5;
    private Double media;
    public GraphDto(FinalResutls f){
        this.createdAt = f.getCreatedAt();
        this.avg5 = f.getAvg5();
        this.media = f.getMedia();
    }
}
