package com.SeriesAnalyzer.main.Services;

import com.SeriesAnalyzer.main.Repositories.Login.IState;
import com.SeriesAnalyzer.main.Models.Login.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class StateService {
    @Autowired
    private IState stateRepository;

    public Optional<State> getStateById(Long id) {
        return stateRepository.findById(id);
    }
}
