package com.cronometro.servicio.servicecube.controller;

import com.cronometro.servicio.servicecube.exceptions.IdInvalidException;
import com.cronometro.servicio.servicecube.models.dtos.ListPreSolvesDto;
import com.cronometro.servicio.servicecube.models.enitties.FinalResutls;
import com.cronometro.servicio.servicecube.services.CronoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cronometro")
public class CuboController {
    @Autowired
    private CronoService cronoService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/graph")
    public ResponseEntity<?> findGrap(){
        return ResponseEntity.ok(cronoService.findGraphData());
    }

    @GetMapping("/{orderby}")
    public ResponseEntity<?> findFinalSolve(@PathVariable("orderby") String orederby, Pageable pageable){
        List<FinalResutls> finalResutls;
        if(orederby.equals("date"))
            finalResutls = cronoService.findByDate(pageable);
        else if(orederby.equals("best"))
            finalResutls = cronoService.findByBestAVG5(pageable);
        else if(orederby.equals("worst"))
            finalResutls = cronoService.findByWorstAvg5(pageable);
        else
            throw new IdInvalidException("Se nececita metodo de ordenamiento /{date|best|worst}");

        return ResponseEntity.ok(finalResutls);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ListPreSolvesDto preSolvesDto){
        return ResponseEntity.status(201).body(cronoService.saveResult(preSolvesDto));
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<?> deleteAll(){
        var res = cronoService.deleteAllSolves();
        simpMessagingTemplate.convertAndSendToUser(res.getUsername(), "/deleteresult/deleteall", res.getTotal());
        return ResponseEntity.noContent().build();
    }
}
