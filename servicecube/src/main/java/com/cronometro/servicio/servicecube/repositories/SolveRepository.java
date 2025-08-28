package com.cronometro.servicio.servicecube.repositories;

import com.cronometro.servicio.servicecube.models.enitties.Solves;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface SolveRepository extends CrudRepository<Solves, UUID> {

}
