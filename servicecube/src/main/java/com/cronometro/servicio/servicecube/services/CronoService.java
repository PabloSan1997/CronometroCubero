package com.cronometro.servicio.servicecube.services;


import com.cronometro.servicio.servicecube.models.dtos.DeleteAllDto;
import com.cronometro.servicio.servicecube.models.dtos.GraphDto;
import com.cronometro.servicio.servicecube.models.dtos.ListPreSolvesDto;
import com.cronometro.servicio.servicecube.models.enitties.FinalResutls;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CronoService {
    List<FinalResutls> findByDate(Pageable pageable);
    List<FinalResutls> findByBestAVG5(Pageable pageable);
    List<FinalResutls> findByWorstAvg5(Pageable pageable);
    List<GraphDto> findGraphData();
    boolean deleteById(UUID id, String username);
    FinalResutls saveResult(ListPreSolvesDto listPreSolvesDto);
    DeleteAllDto deleteAllSolves();
}
