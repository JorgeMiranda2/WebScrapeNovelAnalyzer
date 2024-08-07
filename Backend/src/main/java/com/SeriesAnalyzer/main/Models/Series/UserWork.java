package com.SeriesAnalyzer.main.Models.Series;

import com.SeriesAnalyzer.main.Models.Login.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_work")
public class UserWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "work_id", referencedColumnName = "id", nullable = false)
    private Work work;

    @Column(name="list_name")
    private String listName;

    @Column(name = "status")
    private String status; // Puede ser "siguiendo", "terminada", "abandonada", etc.

}
