package com.SeriesAnalyzer.main.Repositories.Series;

import com.SeriesAnalyzer.main.Models.Series.Anime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAnime extends JpaRepository<Anime, Long> {
    @Query("SELECT a FROM Anime a JOIN a.userWorks uw WHERE uw.user.id = :userId")
    List<Anime> findByUserId(@Param("userId") Long userId);


    @Query("SELECT a FROM Anime a WHERE a.name = :title")
    Optional<Anime> findAnimeByName(@Param("title") String title);

    Page<Anime> findByNameContaining(String name, Pageable pageable);
}
