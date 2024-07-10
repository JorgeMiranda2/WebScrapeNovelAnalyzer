package com.SeriesAnalyzer.main.Repositories.Series;


import com.SeriesAnalyzer.main.Models.Series.Adaptation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdaptation extends JpaRepository<Adaptation, Long> {

}
