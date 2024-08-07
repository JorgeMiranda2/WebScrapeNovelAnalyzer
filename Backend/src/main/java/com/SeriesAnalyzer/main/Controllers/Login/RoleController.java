package com.SeriesAnalyzer.main.Controllers.Login;

import com.SeriesAnalyzer.main.Dtos.AddRoleToUserDto;
import com.SeriesAnalyzer.main.Models.Login.UserRole;
import com.SeriesAnalyzer.main.Services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoleController {
@Autowired
private UserRoleService userRoleService;

    @PostMapping("/addroletouser")
    public ResponseEntity<String> addRoleToUser(@RequestBody AddRoleToUserDto addRoleToUserDto){

        UserRole userRole = userRoleService.addUserRole(addRoleToUserDto);
    return ResponseEntity.status(HttpStatus.OK).body("role added successfully");
    }


}
