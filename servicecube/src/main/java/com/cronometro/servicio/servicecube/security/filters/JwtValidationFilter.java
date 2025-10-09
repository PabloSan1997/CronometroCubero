package com.cronometro.servicio.servicecube.security.filters;


import com.cronometro.servicio.servicecube.exceptions.TokenExpireException;
import com.cronometro.servicio.servicecube.models.dtos.ErrorDto;
import com.cronometro.servicio.servicecube.services.utils.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    private final JwtService jwtService;

    public JwtValidationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer ")){
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");

        try{
            var userDetailsDto = jwtService.validate(token);
            var authtoken = new UsernamePasswordAuthenticationToken(
                    userDetailsDto.getUsername(), null, userDetailsDto.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authtoken);
            chain.doFilter(request, response);
        }
        catch (TokenExpireException e){
            ErrorDto errorDto = new ErrorDto(HttpStatus.UNAUTHORIZED, e.getMessage());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(errorDto.getStatusCode());
            response.getWriter().write(new ObjectMapper().writeValueAsString(errorDto));
        }
        catch (Exception e){
            chain.doFilter(request, response);
        }

    }
}
