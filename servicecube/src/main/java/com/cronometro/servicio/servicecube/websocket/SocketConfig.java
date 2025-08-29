package com.cronometro.servicio.servicecube.websocket;


import com.cronometro.servicio.servicecube.repositories.UserRepository;
import com.cronometro.servicio.servicecube.services.utils.JwtService;
import com.cronometro.servicio.servicecube.websocket.interceptor.JwtInterceptor;
import com.cronometro.servicio.servicecube.websocket.interceptor.UserHandshakeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class SocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/cronoconnect")
                .addInterceptors(new JwtInterceptor(jwtService, userRepository))
                .setHandshakeHandler(new UserHandshakeHandler())
                .setAllowedOriginPatterns("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.enableSimpleBroker("/deleteresult", "/newalgoritm");
        registry.setApplicationDestinationPrefixes("/request");
        registry.setUserDestinationPrefix("/user");
    }
}
