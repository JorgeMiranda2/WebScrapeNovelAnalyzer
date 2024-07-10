package com.SeriesAnalyzer.main.Repositories.Series;


import com.SeriesAnalyzer.main.Models.Series.Novel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INovel extends JpaRepository<Novel, Long> {

}
