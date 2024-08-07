package com.SeriesAnalyzer.main.Models.Series;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.sql.ast.tree.expression.ColumnReference;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("anime")
@Table(name = "anime")
public class Anime extends Work{

    @Column(name = "rating")
    private float rating;

    @Column(name="year")
    private String year;

    @Column(name="image_route")
    private String imageRoute;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "status_of_work_id", referencedColumnName = "id", nullable = false)
    private StatusOfWork statusOfWork;

    @Builder
    public Anime(String name, String synopsis, Author author, List<Genre> genres, float rating, String year, String imageRoute, StatusOfWork statusOfWork) {
        super(name, synopsis, author, genres);
        this.rating = rating;
        this.year = year;
        this.imageRoute = imageRoute;
        this.statusOfWork = statusOfWork;
    }

}
