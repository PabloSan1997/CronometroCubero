package com.cronometro.servicio.servicecube.controller;

import com.cronometro.servicio.servicecube.models.dtos.LoginDto;
import com.cronometro.servicio.servicecube.models.dtos.RegisterDto;
import com.cronometro.servicio.servicecube.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        return ResponseEntity.ok(userService.login(loginDto));
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto){
        return ResponseEntity.ok(userService.register(registerDto));
    }

    @GetMapping("/userinfo")
    public ResponseEntity<?> userinfo(){
        return ResponseEntity.ok(userService.getUserInfo());
    }
}
