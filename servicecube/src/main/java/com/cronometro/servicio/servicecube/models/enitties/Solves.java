package com.cronometro.servicio.servicecube.models.enitties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "solves")
public class Solves {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private Long miliseconds;
    @Column(length = 68)
    private String algoritm;
    @ManyToOne
    @JoinTable(name = "id_finalsolve")
    @JsonIgnore
    private FinalResutls finalResutls;
}
