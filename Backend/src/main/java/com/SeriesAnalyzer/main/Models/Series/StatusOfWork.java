package com.SeriesAnalyzer.main.Models.Series;

import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.signature.qual.Identifier;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="status_of_work")
public class StatusOfWork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", nullable = false,length = 300)
    private String description;

}
