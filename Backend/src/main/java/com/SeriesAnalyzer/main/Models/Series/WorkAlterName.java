package com.SeriesAnalyzer.main.Models.Series;

import jakarta.persistence.*;
import lombok.*;
import com.SeriesAnalyzer.main.Models.Global.Language;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="work_alter_name")
public class WorkAlterName {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "work_id")
    private Work work;

    @ManyToOne
    @JoinColumn(name="alter_name_id")
    private AlterName alterName;

    @ManyToOne
    @JoinColumn(name="language_id")
    private Language language;


}
