package com.cronometro.servicio.servicecube.websocket.interceptor;

import com.cronometro.servicio.servicecube.repositories.UserRepository;
import com.cronometro.servicio.servicecube.services.utils.JwtService;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class JwtInterceptor implements HandshakeInterceptor {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtInterceptor(JwtService jwtService, UserRepository userRepository){
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if(request instanceof  ServletServerHttpRequest reqServlet){
            String jwt = reqServlet.getServletRequest().getParameter("jwt");
            if(jwt == null) return false;
            try{
                var userDetailsDto = jwtService.validate(jwt);
                attributes.put("username", userDetailsDto.getUsername());
                return userRepository.findByUsername(userDetailsDto.getUsername()).isPresent();
            }catch (Exception ignore){
                return false;
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
