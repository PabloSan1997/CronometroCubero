package com.cronometro.servicio.servicecube.controller;

import com.cronometro.servicio.servicecube.models.dtos.AlgoritmDto;
import com.cronometro.servicio.servicecube.models.dtos.IdDto;
import com.cronometro.servicio.servicecube.services.CronoService;
import com.cronometro.servicio.servicecube.services.utils.RubikAlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private CronoService cronoService;
    @Autowired
    private RubikAlgorithmService rubikAlgorithmService;

    @MessageMapping("/deleteone") // /request/deleteone
    public void deleteOneSolve(@Payload IdDto idDto, StompHeaderAccessor headerAccessor){
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        boolean view = cronoService.deleteById(idDto.getId(), username);
        var res = view?idDto:new IdDto();
        simpMessagingTemplate.convertAndSendToUser(username, "/deleteresult/deleteone", res);
    }

    @MessageMapping("/getalgoritm") // /request/getalgoritm
    public void generateAlgorimg(StompHeaderAccessor headerAccessor){
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        AlgoritmDto res = rubikAlgorithmService.getAlgoritm();
        simpMessagingTemplate.convertAndSendToUser(username, "/newalgoritm/one", res);
    }
}
