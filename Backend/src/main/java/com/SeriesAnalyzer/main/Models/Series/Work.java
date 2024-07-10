package com.SeriesAnalyzer.main.Models.Series;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name="work")
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "synopsis", nullable = false, length = 300)
    private String synopsis;

    @OneToMany(mappedBy = "work", cascade = CascadeType.ALL)
    private List<Adaptation> adaptations;

}
