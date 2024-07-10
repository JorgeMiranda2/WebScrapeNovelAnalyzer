package com.SeriesAnalyzer.main.Repositories.Login;


import com.SeriesAnalyzer.main.Models.Login.IdentificationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IIdentificationType extends JpaRepository<IdentificationType, Long> {
}
