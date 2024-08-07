package com.SeriesAnalyzer.main.Repositories.Login;


import com.SeriesAnalyzer.main.Models.Login.IdentificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IIdentificationType extends JpaRepository<IdentificationType, Long> {

    @Query("SELECT it.id FROM IdentificationType it WHERE it.name = :name")
    Optional<Long> getIdentificationTypeByName(@Param("name") String name);
}
