package com.cronometro.servicio.servicecube.models.enitties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "logins")
public class Logins {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String jwt;

    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updtedAt;
    private Boolean state;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users user;

    @PrePersist
    public void prepersist(){
        createdAt = new Date();
        updtedAt = new Date();
        state = true;
    }
    @PreUpdate
    public void preupdate(){
        updtedAt = new Date();
    }

}
