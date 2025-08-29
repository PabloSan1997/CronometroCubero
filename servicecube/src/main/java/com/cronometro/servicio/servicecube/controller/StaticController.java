package com.cronometro.servicio.servicecube.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticController {
    @GetMapping({"/", "/login", "/home", "/results", "/graph/results"})
    public String getMain(){
        return "index";
    }
}
