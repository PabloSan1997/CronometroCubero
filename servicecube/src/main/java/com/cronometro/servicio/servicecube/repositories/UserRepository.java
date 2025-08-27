package com.cronometro.servicio.servicecube.repositories;

import com.cronometro.servicio.servicecube.models.enitties.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<Users, UUID> {
    Optional<Users> findByUsername(String username);
}
