package com.SeriesAnalyzer.main.Repositories.Series;

import com.SeriesAnalyzer.main.Models.Series.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAnime extends JpaRepository<Anime, Long> {
    // Puedes agregar métodos personalizados de consulta si es necesario
}
