package com.cronometro.servicio.servicecube.services.utils;

import com.cronometro.servicio.servicecube.exceptions.AuthJwtException;
import com.cronometro.servicio.servicecube.exceptions.IdInvalidException;
import com.cronometro.servicio.servicecube.exceptions.RefreshTokenException;
import com.cronometro.servicio.servicecube.exceptions.TokenExpireException;
import com.cronometro.servicio.servicecube.models.dtos.RefreshTokenDto;
import com.cronometro.servicio.servicecube.models.dtos.UserDetailsDto;
import com.cronometro.servicio.servicecube.models.dtos.UserInfoDto;
import com.cronometro.servicio.servicecube.models.enitties.Logins;
import com.cronometro.servicio.servicecube.models.enitties.Users;
import com.cronometro.servicio.servicecube.repositories.LoginRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class JwtService {

    @Autowired
    private LoginRepository loginRepository;

    @Value("${mi.secretkey.jwt}")
    private String secretkey;
    @Value("${mi.secretkey.jwtrefresh}")
    private String refreshkey;
    @Value("${mi.secretkey.jwtsocket}")
    private String webtokensocket;

    private SecretKey getKey(String keystring) {
        byte[] ky = Decoders.BASE64.decode(keystring);
        return Keys.hmacShaKeyFor(ky);
//        return Jwts.SIG.HS256.key().build();
    }

    public UserDetailsDto validate(String token) throws Exception {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getKey(secretkey)).build().parseSignedClaims(token).getPayload();
            String username = claims.getSubject();
            String nickname = (String) claims.get("nickname");
            List<String> authoritiesString = claims.get("authorities", List.class);
            Collection<? extends GrantedAuthority> auhtorities = authoritiesString.stream()
                    .map(SimpleGrantedAuthority::new).toList();

            return new UserDetailsDto(username, nickname, "", auhtorities, true);
        } catch (ExpiredJwtException e) {
            throw new TokenExpireException();
        } catch (Exception e) {
            throw new AuthJwtException(e.getMessage());
        }
    }

    public String sign(UserDetailsDto userDetailsDto) {

        List<String> authnames = userDetailsDto.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        Claims claims = Jwts.claims()
                .add("authorities", authnames)
                .add("nickname", userDetailsDto.getNickname()).build();

        return Jwts.builder().signWith(getKey(secretkey)).claims(claims)
                .subject(userDetailsDto.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 7))
                .compact();

    }

    @Transactional
    public String signrefresh(Users user) {
        Logins logins = loginRepository.save(Logins.builder().user(user).jwt("").build());
        Claims claims = Jwts.claims().add("idjwt", logins.getId().toString()).build();
        String resfreshToken = Jwts.builder().signWith(getKey(refreshkey)).claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .issuedAt(new Date())
                .subject(user.getUsername()).compact();
        logins.setJwt(resfreshToken);
        loginRepository.save(logins);
        return resfreshToken;
    }

    @Transactional
    public RefreshTokenDto validateRefresh(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getKey(refreshkey)).build().parseSignedClaims(token).getPayload();
            String username = claims.getSubject();
            UUID idjwt = UUID.fromString((String) claims.get("idjwt"));
            Logins logins = loginRepository.findByUsernameAndId(username, idjwt).orElseThrow(RefreshTokenException::new);
            if (!logins.getState()) throw new RefreshTokenException();
            return RefreshTokenDto.builder().idToken(idjwt).username(username).build();
        } catch (Exception e) {
            throw new RefreshTokenException();
        }
    }

    public String signSocket(UserInfoDto userInfoDto) {
        Claims claims = Jwts.claims()
                .add("nickname", userInfoDto.getNickname()).build();

        return Jwts.builder().signWith(getKey(webtokensocket)).claims(claims)
                .subject(userInfoDto.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .compact();
    }

    public UserInfoDto validateSocket(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getKey(webtokensocket)).build().parseSignedClaims(token).getPayload();
            String username = claims.getSubject();
            String nickname = (String) claims.get("nickname");

            return new UserInfoDto(username, nickname);
        } catch (ExpiredJwtException e) {
            throw new TokenExpireException();
        } catch (Exception e) {
            throw new AuthJwtException(e.getMessage());
        }
    }

    public void logout(UUID idToken, Users user) {
        loginRepository.findByUsernameAndId(user.getUsername(), idToken).ifPresent(p -> {
            p.setState(false);
            loginRepository.save(p);
        });
    }
}
