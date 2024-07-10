package com.SeriesAnalyzer.main.Repositories.Series;


import com.SeriesAnalyzer.main.Models.Series.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IGenre extends JpaRepository<Genre, Long> {
    // Puedes agregar métodos personalizados de consulta si es necesario
}
