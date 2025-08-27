package com.cronometro.servicio.servicecube.models.dtos;

import com.cronometro.servicio.servicecube.models.enitties.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto implements UserDetails {

    private String username;
    @Getter
    private String nickname;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setAuthoriteisAsRoles(List<Roles> roles){
        authorities = roles.stream()
                .map(p -> new SimpleGrantedAuthority("ROLE_"+p.getName())).toList();
    }
}
