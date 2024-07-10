package com.SeriesAnalyzer.main.Models.Login;

import com.SeriesAnalyzer.main.Models.Series.Adaptation;
import com.SeriesAnalyzer.main.Models.Series.Anime;
import com.SeriesAnalyzer.main.Models.Series.Manga;
import com.SeriesAnalyzer.main.Models.Series.Novel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "state_id", referencedColumnName = "id", nullable = false)
    private State state;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    private Person person;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<UserRole> userRoles = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_adaptation",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "adaptation_id")
    )
    private List<Anime> animes;

    @ManyToMany
    @JoinTable(
            name = "user_adaptation",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "adaptation_id")
    )
    private List<Manga> mangas;

    @ManyToMany
    @JoinTable(
            name = "user_adaptation",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "adaptation_id")
    )
    private List<Novel> novels;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getRoleName()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Aquí puedes agregar lógica para controlar si la cuenta ha expirado
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Aquí puedes agregar lógica para controlar si la cuenta está bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Aquí puedes agregar lógica para controlar si las credenciales han expirado
    }

    @Override
    public boolean isEnabled() {
        return true; // Aquí puedes agregar lógica para controlar si la cuenta está habilitada
    }
}
