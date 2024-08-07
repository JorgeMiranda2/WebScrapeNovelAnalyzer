package com.SeriesAnalyzer.main.Repositories.Series;

import com.SeriesAnalyzer.main.Models.Series.Work;
import org.springframework.data.jpa.repository.JpaRepository;



public interface IWork extends JpaRepository<Work, Long>,WorkRepositoryCustom {
}
