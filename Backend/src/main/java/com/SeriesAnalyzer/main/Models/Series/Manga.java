package com.SeriesAnalyzer.main.Models.Series;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("manga")
@Table(name = "manga")
public class Manga extends Work{
    @Column(name = "rating")
    private float rating = 0;

    @Column(name="year")
    private String year;

    @Column(name="demography")
    private String demography = "unknown";

    @Column(name="image_route")
    private String imageRoute = "";

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "status_of_work_id", referencedColumnName = "id", nullable = false)
    private StatusOfWork statusOfWork;


}
