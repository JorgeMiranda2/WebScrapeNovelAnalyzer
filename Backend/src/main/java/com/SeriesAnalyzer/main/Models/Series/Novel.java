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
@DiscriminatorValue("novel")
@Table(name = "novel")
public class Novel extends Work{

    @Column(name="language")
    private String language;

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

    @ManyToMany
    @JoinTable(
            name = "novel_tag",
            joinColumns = @JoinColumn(name = "novel_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

}
