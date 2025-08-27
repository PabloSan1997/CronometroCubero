package com.cronometro.servicio.servicecube.services.utils;

import com.cronometro.servicio.servicecube.models.enitties.Roles;
import com.cronometro.servicio.servicecube.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

@Service
public class InitService {
    @Autowired
    private RoleRepository roleRepository;
    @Value("${server.port}")
    private String port;


    public void init(){
        String[] rolesname = {"USER", "ADMIN", "WRITTER", "ASSISTANT"};

        List<Roles> roles = new ArrayList<>();

        for(String name:rolesname){
            if(roleRepository.findByName(name).isEmpty())
                roles.add(Roles.builder().name(name).build());
        }
        if(!roles.isEmpty())
            roleRepository.saveAll(roles);

        System.out.println("Port: "+port);
    }
}
