package com.SeriesAnalyzer.main.Models.Login;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @Column(name = "identification")
    private String identification;

    //@Column(name = "type_identification")
    @ManyToOne
    @JoinColumn(name = "type_identification", referencedColumnName = "id")
    private IdentificationType identificationType;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private User user;



}
