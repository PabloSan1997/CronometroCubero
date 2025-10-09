package com.cronometro.servicio.servicecube.services;

import com.cronometro.servicio.servicecube.models.dtos.*;

public interface UserService {
    LoginTokenDto login(LoginDto loginDto);
    LoginTokenDto register(RegisterDto registerDto);
    UserInfoDto getUserInfo();
    TokenDto reresh(String token);
    TokenDto getSocketToken(String token);
    void logout(String token);
}
