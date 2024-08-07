package com.SeriesAnalyzer.main.Repositories.Series;

import com.SeriesAnalyzer.main.Models.Series.Manga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IManga extends JpaRepository<Manga, Long> {
    @Query("SELECT m FROM Manga m JOIN m.userWorks uw WHERE uw.user.id = :userId")
    List<Manga> findByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(m) > 0 FROM Manga m WHERE LOWER(m.name) = LOWER(:nameWork)")
    Boolean existsByName(@Param("nameWork") String nameWork);


    @Query("SELECT m FROM Manga m WHERE LOWER(m.name) = LOWER(:mangaName)")
    Optional<Manga> findByName(@Param("mangaName") String mangaName);

    Page<Manga> findByNameContaining(String name, Pageable pageable);
}
