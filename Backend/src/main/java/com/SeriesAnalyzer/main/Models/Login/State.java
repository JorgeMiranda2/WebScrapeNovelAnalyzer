package com.SeriesAnalyzer.main.Models.Login;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "state")
public class State {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "state_type_id", referencedColumnName = "id", nullable = false)
    private StateType stateType;

    @Column(name = "name", nullable = false)
    private String name;


}
