package com.cronometro.servicio.servicecube.repositories;

import com.cronometro.servicio.servicecube.models.enitties.Logins;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface LoginRepository extends CrudRepository<Logins, UUID> {
    @Query("select l from Logins l where l.user.username = ?1 and l.id=?2")
    Optional<Logins> findByUsernameAndId(String username, UUID id);
}
