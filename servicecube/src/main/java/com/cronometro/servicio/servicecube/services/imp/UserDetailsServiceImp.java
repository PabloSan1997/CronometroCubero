package com.cronometro.servicio.servicecube.services.imp;

import com.cronometro.servicio.servicecube.models.dtos.UserDetailsDto;
import com.cronometro.servicio.servicecube.models.enitties.Roles;
import com.cronometro.servicio.servicecube.models.enitties.Users;
import com.cronometro.servicio.servicecube.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("no"));
        String hashpassword = user.getPassword();
        String nickname = user.getNickname();
        Boolean enabled = user.getEnabled();
        List<Roles> roles = user.getRoles();
        var userDetailsDto = new UserDetailsDto(username, nickname, hashpassword, null, enabled);
        userDetailsDto.setAuthoriteisAsRoles(roles);
        return userDetailsDto;
    }
}
