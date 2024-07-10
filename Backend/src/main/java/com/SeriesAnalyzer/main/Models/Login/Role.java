package com.SeriesAnalyzer.main.Models.Login;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
@Setter
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100) // Ejemplo de limitación: Not null y máximo de 100 caracteres
    private String name;

    @Column(name = "role_name", nullable = false, length = 100) // Ejemplo de limitación: Not null y máximo de 100 caracteres
    private String roleName;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<Permission> permissions;


    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
