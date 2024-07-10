package com.SeriesAnalyzer.main.Models.Series;


import com.SeriesAnalyzer.main.Models.Login.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;



@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "adaptation_type")

@Table(name="adaptation")
public abstract class Adaptation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "work_id")
    private Work work;


}
