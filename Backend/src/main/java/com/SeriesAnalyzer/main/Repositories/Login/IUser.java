package com.SeriesAnalyzer.main.Repositories.Login;

import com.SeriesAnalyzer.main.Models.Login.User;
import com.SeriesAnalyzer.main.Models.Series.UserWork;
import com.SeriesAnalyzer.main.Models.Series.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface IUser extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("SELECT u.id FROM User u WHERE u.username = :username")
    public Optional<Long> getUserIdFromUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    public Optional<User> getUserFromUsername(String username);
}
