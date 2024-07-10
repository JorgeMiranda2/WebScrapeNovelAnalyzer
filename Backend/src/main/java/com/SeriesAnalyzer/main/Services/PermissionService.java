package com.SeriesAnalyzer.main.Services;

import com.SeriesAnalyzer.main.Dtos.PermissionDto;
import com.SeriesAnalyzer.main.Repositories.Login.IPermission;
import com.SeriesAnalyzer.main.Models.Login.Permission;

import com.SeriesAnalyzer.main.Models.Login.Role;
import com.SeriesAnalyzer.main.Models.Login.State;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PermissionService {
    private static final Logger logger = LoggerFactory.getLogger(PermissionService.class);

    @Autowired
    private IPermission permissionRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private StateService stateService;

    @Cacheable("permissions")
    public List<Permission> getAllPermissions() {
        try {
            return permissionRepository.findAll();
        } catch (Exception e) {
            logger.error("Error fetching all permissions", e);
            throw new RuntimeException("Error fetching all permissions", e);
        }
    }

    public void createNewPermission(PermissionDto permissionDto) {
        try {
            Optional<Role> possibleRole = roleService.getRoleById(permissionDto.getRoleId());
            Optional<State> possibleState = stateService.getStateById(permissionDto.getRoleId());

            if (possibleRole.isPresent() && possibleState.isPresent()) {
                permissionRepository.save(
                        Permission.builder()
                                .route(permissionDto.getRoute())
                                .method(permissionDto.getMethod())
                                .role(possibleRole.get())
                                .state(possibleState.get())
                                .build()
                );
            } else {
                throw new IllegalArgumentException("Role or State not found");
            }
        } catch (Exception e) {
            logger.error("Error creating new permission", e);
            throw new RuntimeException("Error creating new permission", e);
        }
    }
}