package com.SeriesAnalyzer.main.Models.Series;


import com.SeriesAnalyzer.main.Models.Login.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("manga")
@Table(name = "manga")
public class Manga extends Adaptation{
    @Column(name = "rating")
    private String rating;

    @Column(name="year")
    private String year;

    @Column(name="demography")
    private String demography;

    @ManyToMany(mappedBy = "adaptations")
    private List<User> users;

}
