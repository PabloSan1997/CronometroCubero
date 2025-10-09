package com.cronometro.servicio.servicecube.security;

import com.cronometro.servicio.servicecube.security.filters.JwtValidationFilter;
import com.cronometro.servicio.servicecube.services.utils.InitService;
import com.cronometro.servicio.servicecube.services.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
public class SecurityConfig {
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtService jwtService) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(a->a
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/user/login",
                                "/api/user/register",
                                "/api/user/refreshauth",
                                "/api/user/gettokensocket",
                                "/api/user/logout"
                        ).permitAll()
                        .requestMatchers(
                                "/api/cronometro",
                                "/api/cronometro/**"
                        ).hasRole("USER")
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/user/userinfo"
                        ).hasRole("USER")
                        .requestMatchers(
                                "/cronoconnect", "/", "/assets", "/assets/**", "/login",
                                "logo.svg", "index.html", "/home", "/results", "/graph/results"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(new JwtValidationFilter(authenticationManager(), jwtService))
                .cors(c -> c.configurationSource(corsConfigurationSource()));

        return http.build();
    }
    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET","POST", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    CommandLineRunner commandLineRunner(InitService initService){
        return args -> {
            initService.init();
        };
    }
}
