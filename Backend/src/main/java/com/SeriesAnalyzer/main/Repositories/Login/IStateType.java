package com.SeriesAnalyzer.main.Repositories.Login;

import com.SeriesAnalyzer.main.Models.Login.StateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface IStateType extends JpaRepository<StateType, Long> {
}
