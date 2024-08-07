package com.SeriesAnalyzer.main.Models.Series;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.signature.qual.Identifier;

import java.util.ArrayList;
import java.util.List;

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
    private String description = "";

    @JsonIgnore
    @OneToMany(mappedBy = "statusOfWork")
    private List<Anime> animes= new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "statusOfWork")
    private List<Manga> mangas = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "statusOfWork")
    private List<Novel> novels= new ArrayList<>();

}
