package com.cronometro.servicio.servicecube.models.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Principal;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrincipalDto implements Principal  {
    private String name;
    @Override
    public String getName() {
        return name;
    }
}
