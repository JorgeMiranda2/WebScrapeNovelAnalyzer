package com.SeriesAnalyzer.main.Models.Series;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "work_type")
@Table(name = "work")
public abstract class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 500)
    private String name;

    @Column(name = "synopsis", nullable = false, length = 1000)
    private String synopsis;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @JsonIgnore
    @OneToMany(mappedBy = "work")
    private List<UserWork> userWorks = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "work_genre",
            joinColumns = @JoinColumn(name = "work_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres = new ArrayList<>();

    // Constructor protegido para ser usado por las subclases
    protected Work(String name, String synopsis, Author author, List<Genre> genres) {
        this.name = name;
        this.synopsis = synopsis;
        this.author = author;
        this.genres = genres != null ? genres : new ArrayList<>();
    }


}
