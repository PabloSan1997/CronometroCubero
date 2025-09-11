package com.cronometro.servicio.servicecube.repositories;

import com.cronometro.servicio.servicecube.models.enitties.Solves;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SolveRepository extends CrudRepository<Solves, UUID> {


    @Modifying
    @Query("delete from Solves s where s.finalResutls.user.username=:username")
    int deleteByUsername(@Param("username") String username);
}
