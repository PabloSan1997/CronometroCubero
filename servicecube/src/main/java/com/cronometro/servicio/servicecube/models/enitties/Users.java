package com.cronometro.servicio.servicecube.models.enitties;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, length = 60)
    private String username;
    @Column(length = 60)
    private String nickname;
    @Column(length = 600)
    @JsonIgnore
    private String password;
    private Boolean enabled;
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"id_user", "id_role"})}
    )
    @JsonIgnore
    private List<Roles> roles;
    @OneToMany(mappedBy = "user")
    private List<FinalResutls> finalResutls;
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Logins> logins;

    @PrePersist
    public void prepresist(){
        enabled = true;
    }
}
