package com.cronometro.servicio.servicecube.repositories;

import com.cronometro.servicio.servicecube.models.enitties.Roles;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends CrudRepository<Roles, UUID> {
    Optional<Roles> findByName(String name);
}
