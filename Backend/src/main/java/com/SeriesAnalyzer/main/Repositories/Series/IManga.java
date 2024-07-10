package com.SeriesAnalyzer.main.Repositories.Series;

import com.SeriesAnalyzer.main.Models.Series.Manga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IManga extends JpaRepository<Manga, Long> {

}
