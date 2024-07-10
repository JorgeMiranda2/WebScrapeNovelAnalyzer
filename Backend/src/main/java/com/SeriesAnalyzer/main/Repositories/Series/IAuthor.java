package com.SeriesAnalyzer.main.Repositories.Series;


import com.SeriesAnalyzer.main.Models.Series.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IAuthor extends JpaRepository<Author, Long> {
    // Puedes agregar métodos personalizados de consulta si es necesario
}
