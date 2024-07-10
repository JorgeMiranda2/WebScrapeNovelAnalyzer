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
@DiscriminatorValue("anime")
@Table(name = "anime")
public class Anime extends Adaptation{

    @Column(name = "rating")
    private String rating;

    @Column(name="year")
    private String year;

    @ManyToMany(mappedBy = "adaptations")
    private List<User> users;


}
