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
@DiscriminatorValue("novel")
@Table(name = "novel")
public class Novel extends Adaptation{

    @Column(name="language")
    private String language;

    @Column(name = "rating")
    private String rating;

    @Column(name="year")
    private String year;

    @ManyToMany(mappedBy = "adaptations")
    private List<User> users;
}
