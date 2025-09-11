package com.cronometro.servicio.servicecube.repositories;

import com.cronometro.servicio.servicecube.models.dtos.GraphDto;
import com.cronometro.servicio.servicecube.models.enitties.FinalResutls;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FinalResultRepository extends CrudRepository<FinalResutls, UUID> {

    @Query("select f from FinalResutls f where f.user.username = :username order by f.avg5 asc")
    List<FinalResutls> findAllBuAvgBestAndUsername(@Param("username") String username ,Pageable pageable);

    @Query("select f from FinalResutls f where f.user.username = :username order by f.avg5 desc")
    List<FinalResutls> findAllBuAvgWorstAndUsername(@Param("username") String username ,Pageable pageable);

    @Query("select f from FinalResutls f where f.user.username = :username order by f.createdAt desc")
    List<FinalResutls> findAllByOrderByCreatedAtAndUsername(@Param("username") String username, Pageable pageable);

    @Query("select new com.cronometro.servicio.servicecube.models.dtos.GraphDto(g) from FinalResutls g where g.user.username = ?1 order by g.createdAt asc")
    List<GraphDto> findAllForGraph(String username);

    @Query("select f from FinalResutls f where f.id = :id and f.user.username = :username")
    Optional<FinalResutls> findByIdAndUsername(@Param("id") UUID id, @Param("username") String username);

    @Modifying
    @Query("delete from FinalResutls f where f.user.username=:username")
    int deleteByUsername(@Param("username") String username);
}
