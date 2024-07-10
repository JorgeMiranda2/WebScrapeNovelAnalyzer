package com.SeriesAnalyzer.main.Repositories.Login;

import com.SeriesAnalyzer.main.Models.Login.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPermission extends JpaRepository<Permission, Long> {
}
