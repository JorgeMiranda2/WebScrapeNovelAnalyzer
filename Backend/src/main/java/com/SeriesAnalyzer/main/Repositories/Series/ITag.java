package com.SeriesAnalyzer.main.Repositories.Series;


import com.SeriesAnalyzer.main.Models.Series.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITag extends JpaRepository<Tag, Long> {
    // Puedes agregar métodos personalizados de consulta si es necesario
}