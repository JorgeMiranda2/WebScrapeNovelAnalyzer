package com.SeriesAnalyzer.main.Services;

import com.SeriesAnalyzer.main.Repositories.Login.IRole;
import com.SeriesAnalyzer.main.Models.Login.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class RoleService {

    @Autowired
    private IRole roleRepository;

    @Cacheable("roles")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public List<String> getAllRoleNames() {
        return getAllRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
    }
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }
}
