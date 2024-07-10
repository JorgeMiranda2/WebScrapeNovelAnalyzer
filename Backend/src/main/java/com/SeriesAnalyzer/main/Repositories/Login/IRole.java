package com.SeriesAnalyzer.main.Repositories.Login;

import com.SeriesAnalyzer.main.Models.Login.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface IRole extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String roleName);
}
