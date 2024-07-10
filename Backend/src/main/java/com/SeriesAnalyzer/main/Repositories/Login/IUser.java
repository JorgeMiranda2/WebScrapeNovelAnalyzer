package com.SeriesAnalyzer.main.Repositories.Login;

import com.SeriesAnalyzer.main.Models.Login.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface IUser extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
}
