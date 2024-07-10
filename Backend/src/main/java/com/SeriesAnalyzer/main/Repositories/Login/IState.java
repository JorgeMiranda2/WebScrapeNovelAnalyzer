package com.SeriesAnalyzer.main.Repositories.Login;

import com.SeriesAnalyzer.main.Models.Login.State;
import com.SeriesAnalyzer.main.Models.Login.StateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface IState extends JpaRepository<State, Long> {
    Optional<State> findByName(String name);
    Optional<State> findByNameAndStateType(String name, StateType stateType);
}
