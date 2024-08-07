package com.SeriesAnalyzer.main.Repositories.Series;

import com.SeriesAnalyzer.main.Models.Series.WorkAlterName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWorkAlterName extends JpaRepository<WorkAlterName,Long>, WorkRepositoryCustom {
}
