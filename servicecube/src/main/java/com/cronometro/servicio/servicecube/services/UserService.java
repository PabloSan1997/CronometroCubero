package com.cronometro.servicio.servicecube.services;

import com.cronometro.servicio.servicecube.models.dtos.LoginDto;
import com.cronometro.servicio.servicecube.models.dtos.RegisterDto;
import com.cronometro.servicio.servicecube.models.dtos.TokenDto;
import com.cronometro.servicio.servicecube.models.dtos.UserInfoDto;

public interface UserService {
    TokenDto login(LoginDto loginDto);
    TokenDto register(RegisterDto registerDto);
    UserInfoDto getUserInfo();
}
