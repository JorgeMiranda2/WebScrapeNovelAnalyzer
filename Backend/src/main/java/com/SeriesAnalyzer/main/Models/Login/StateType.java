package com.SeriesAnalyzer.main.Models.Login;
import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "state_type")
public class StateType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100) // Ejemplo de limitación: Not null y máximo de 100 caracteres
    private String name;


}