package com.cronometro.servicio.servicecube.services.imp;

import com.cronometro.servicio.servicecube.exceptions.IdInvalidException;
import com.cronometro.servicio.servicecube.exceptions.UsernamePasswordException;
import com.cronometro.servicio.servicecube.models.dtos.*;
import com.cronometro.servicio.servicecube.models.enitties.Roles;
import com.cronometro.servicio.servicecube.models.enitties.Users;
import com.cronometro.servicio.servicecube.repositories.LoginRepository;
import com.cronometro.servicio.servicecube.repositories.RoleRepository;
import com.cronometro.servicio.servicecube.repositories.UserRepository;
import com.cronometro.servicio.servicecube.services.UserService;
import com.cronometro.servicio.servicecube.services.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @Override
    @Transactional
    public LoginTokenDto login(LoginDto loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        var authtoken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication authentication = authenticationManager.authenticate(authtoken);
            var userdetailsdto = (UserDetailsDto) authentication.getPrincipal();
            String token = jwtService.sign(userdetailsdto);
            String refreshToken = jwtService.signrefresh(userRepository.findByUsername(username).orElseThrow());
            return LoginTokenDto.builder().jwt(token).refreshToken(refreshToken).build();
        }
        catch (Exception ignore){
            throw new UsernamePasswordException("Username o password incorrectos");
        }

    }

    @Override
    @Transactional
    public LoginTokenDto register(RegisterDto registerDto) {
        var vuser = userRepository.findByUsername(registerDto.getUsername());
        if(vuser.isPresent())
            throw new UsernamePasswordException("Username ocupado");

        String username = registerDto.getUsername();
        String password = registerDto.getPassword();
        String nickname = registerDto.getNickname();

        Roles role = roleRepository.findByName("USER")
                .orElseThrow(()-> new RuntimeException("Problemas con el servidor, no se pueden registrar usuario"));

        Users preuser = Users.builder().nickname(nickname)
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(List.of(role)).build();
        Users user = userRepository.save(preuser);

        return login(LoginDto.builder().username(user.getUsername()).password(password).build());
    }

    @Override
    @Transactional
    public UserInfoDto getUserInfo() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userRepository.findByUsername(username)
                .orElseThrow(()-> new IdInvalidException("no existe usuario autenticado"));
        return UserInfoDto.builder().nickname(user.getNickname())
                .username(user.getUsername()).build();
    }

    @Override
    @Transactional
    public TokenDto reresh(String token) {
        RefreshTokenDto refreshTokenDto = jwtService.validateRefresh(token);
        Users user = userRepository.findByUsername(refreshTokenDto.getUsername()).orElseThrow(RuntimeException::new);
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setUsername(refreshTokenDto.getUsername());
        userDetailsDto.setEnabled(user.getEnabled());
        userDetailsDto.setNickname(user.getNickname());
        userDetailsDto.setAuthoriteisAsRoles(user.getRoles());
        String jwt = jwtService.sign(userDetailsDto);
        return new TokenDto(jwt);
    }

    @Override
    @Transactional
    public TokenDto getSocketToken(String token) {
        RefreshTokenDto refreshTokenDto = jwtService.validateRefresh(token);
        Users user = userRepository.findByUsername(refreshTokenDto.getUsername()).orElseThrow(RuntimeException::new);
        UserInfoDto userInfoDto = UserInfoDto.builder()
                .nickname(user.getNickname()).username(user.getUsername()).build();
        String jwt = jwtService.signSocket(userInfoDto);
        return new TokenDto(jwt);
    }

    @Override
    @Transactional
    public void logout(String token) {
        RefreshTokenDto refreshTokenDto = jwtService.validateRefresh(token);
        Users user = userRepository.findByUsername(refreshTokenDto.getUsername()).orElseThrow(RuntimeException::new);
        jwtService.logout(refreshTokenDto.getIdToken(), user);
    }
}
