package com.SeriesAnalyzer.main.Repositories.Series;


import com.SeriesAnalyzer.main.Models.Series.StatusOfWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface IStatusOfWork extends JpaRepository<StatusOfWork, Long> {
    // Puedes agregar métodos personalizados de consulta si es necesario
}