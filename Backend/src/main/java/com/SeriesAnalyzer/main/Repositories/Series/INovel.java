package com.SeriesAnalyzer.main.Repositories.Series;


import com.SeriesAnalyzer.main.Models.Series.Novel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INovel extends JpaRepository<Novel, Long> {

    @Query("SELECT n FROM Novel n JOIN n.userWorks uw WHERE uw.user.id = :userId")
    List<Novel> findByUserId(@Param("userId") Long userId);
}
