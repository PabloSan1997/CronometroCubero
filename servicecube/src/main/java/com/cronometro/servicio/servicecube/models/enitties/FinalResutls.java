package com.cronometro.servicio.servicecube.models.enitties;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "final_result")
public class FinalResutls {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Double max;
    private Double min;
    private Double media;
    private Double avg5;
    @Column(name = "created_at")
    private Date createdAt;
    @OneToMany(mappedBy = "finalResutls")
    private List<Solves> solves;
    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private Users user;

    @PrePersist
    public void prepersist(){
        createdAt = new Date();
    }
}
