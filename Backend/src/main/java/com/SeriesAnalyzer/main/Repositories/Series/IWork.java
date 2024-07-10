package com.SeriesAnalyzer.main.Repositories.Series;

import com.SeriesAnalyzer.main.Models.Series.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWork extends JpaRepository<Work, Long> {

}
