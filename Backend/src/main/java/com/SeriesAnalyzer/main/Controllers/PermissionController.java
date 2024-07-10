package com.SeriesAnalyzer.main.Controllers;

import com.SeriesAnalyzer.main.Dtos.PermissionDto;
import com.SeriesAnalyzer.main.Services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @PostMapping("/createpermission")
    public ResponseEntity<String> createPermission(@RequestBody PermissionDto permissionDto){

        permissionService.createNewPermission(permissionDto);
        return ResponseEntity.status(HttpStatus.OK).body("permission created");
    }
}
