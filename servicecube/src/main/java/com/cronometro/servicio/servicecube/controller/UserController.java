package com.cronometro.servicio.servicecube.controller;

import com.cronometro.servicio.servicecube.models.dtos.LoginDto;
import com.cronometro.servicio.servicecube.models.dtos.RegisterDto;
import com.cronometro.servicio.servicecube.models.dtos.TokenDto;
import com.cronometro.servicio.servicecube.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        var res = userService.login(loginDto);
        ResponseCookie cookie = ResponseCookie.from("mitoken", res.getRefreshToken())
                .httpOnly(true)
                .sameSite("Lax")
                .secure(true) //https true
                .maxAge(  60 * 60 * 24 * 7)
                .path("/")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new TokenDto(res.getJwt()));
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto){
        var res = userService.register(registerDto);
        ResponseCookie cookie = ResponseCookie.from("mitoken", res.getRefreshToken())
                .httpOnly(true)
                .sameSite("Lax")
                .secure(true) //https true
                .maxAge(  60 * 60 * 24 * 7)
                .path("/")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new TokenDto(res.getJwt()));
    }

    @GetMapping("/userinfo")
    public ResponseEntity<?> userinfo(){
        return ResponseEntity.ok(userService.getUserInfo());
    }

    @PostMapping("/refreshauth")
    public ResponseEntity<?> refreshaut(@CookieValue(name = "mitoken") String mitoken){
        return ResponseEntity.ok(userService.reresh(mitoken));
    }
    @PostMapping("/gettokensocket")
    public ResponseEntity<?> getsockettoken(@CookieValue(name = "mitoken") String mitoken){
        return ResponseEntity.ok(userService.getSocketToken(mitoken));
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(name = "mitoken") String mitoken){
        userService.logout(mitoken);
        ResponseCookie cookie = ResponseCookie.from("mitoken", "")
                .httpOnly(true)
                .sameSite("Lax")
                .secure(true) //https true
                .maxAge(  0)
                .path("/")
                .build();
        return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }
}
