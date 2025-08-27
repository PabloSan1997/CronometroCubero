package com.cronometro.servicio.servicecube.services.utils;

import com.cronometro.servicio.servicecube.models.dtos.UserDetailsDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    @Value("${mi.secretkey.jwt}")
    private String secretkey;

    private SecretKey getKey(){
        byte[] ky = Decoders.BASE64URL.decode(secretkey);
        return Keys.hmacShaKeyFor(ky);
//        return Jwts.SIG.HS256.key().build();
    }

    public UserDetailsDto validate(String token) throws Exception{
        Claims claims = Jwts.parser()
                .verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
        String username = claims.getSubject();
        String nickname = (String) claims.get("nickname");
       List<String> authoritiesString =  claims.get("authorities", List.class);
        Collection<? extends  GrantedAuthority> auhtorities = authoritiesString.stream()
                .map(SimpleGrantedAuthority::new).toList();

        return new UserDetailsDto(username, nickname, "", auhtorities, true);
    }

    public String sign(UserDetailsDto userDetailsDto){
        List<String> authnames = userDetailsDto.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        Claims claims = Jwts.claims()
                .add("authorities", authnames)
                .add("nickname", userDetailsDto.getNickname()).build();

        return Jwts.builder().signWith(getKey()).claims(claims)
                .subject(userDetailsDto.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .compact();
    }
}
