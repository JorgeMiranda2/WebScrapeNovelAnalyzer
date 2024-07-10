package com.SeriesAnalyzer.main.Controllers;


import com.SeriesAnalyzer.main.Dtos.AccessResponseDto;
import com.SeriesAnalyzer.main.Dtos.AccountDto;
import com.SeriesAnalyzer.main.Dtos.LoginDto;
import com.SeriesAnalyzer.main.Repositories.Login.IPerson;
import com.SeriesAnalyzer.main.Repositories.Login.IRole;
import com.SeriesAnalyzer.main.Repositories.Login.IUser;
import com.SeriesAnalyzer.main.Models.Login.User;
import com.SeriesAnalyzer.main.Security.JwtAuthenticationProvider;
import com.SeriesAnalyzer.main.Security.SecurityConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import com.SeriesAnalyzer.main.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import java.net.URI;
import java.util.HashMap;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private IUser userRepository;
    @Autowired
    private IPerson personRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IRole roleRepository;
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    private static long cookieTime = SecurityConstants.JWT_EXPIRATION_TOKEN;

    @GetMapping("/prueba")
    public String createAccount() {
      return "He vuelto crack";
    }

    @PostMapping("/createaccount")
    public ResponseEntity<String> createAccount(@RequestBody AccountDto accountDto) {
        if(userRepository.existsByUsername(accountDto.getUsername())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user already  exists ");
        }

        try {
            HashMap<String, Object> registeredEntities = userService.registerUserAndPerson(accountDto, "USER");
            System.out.println("hashmap: " + registeredEntities.get("user"));
            User registeredUser = (User) registeredEntities.get("user");


            URI location = new URI("/api/users/" + registeredUser.getId());
            return ResponseEntity.created(location).body("Account created successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating account: " + e.getMessage());
        }
    }

    @PostMapping("/createaccountadmin")
    public ResponseEntity<String> createAccountadmin(@RequestBody AccountDto accountDto) {
        if(userRepository.existsByUsername(accountDto.getUsername())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user already  exists ");
        }

        try {
            HashMap<String, Object> registeredEntities = userService.registerUserAndPerson(accountDto, "ADMIN");
            System.out.println("hashmap: " + registeredEntities.get("user"));
            User registeredUser = (User) registeredEntities.get("user");


            URI location = new URI("/api/users/" + registeredUser.getId());
            return ResponseEntity.created(location).body("Account created successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating account: " + e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<AccessResponseDto> signIn(@RequestBody LoginDto loginDto, HttpServletResponse response) {


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getIdentifier(), loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtAuthenticationProvider.createToken(authentication);

        String username = null;

        try {
            User user = userRepository.findByUsername(loginDto.getIdentifier()).orElseThrow();
            username = user.getUsername();
            // Crear la cookie para el token
            ResponseCookie tokenCookie = ResponseCookie.from("token", token)
                    .httpOnly(true)
                    .secure(true) // Asegúrate de usar HTTPS en producción
                    .path("/")
                    .maxAge(cookieTime) // Duración de la cookie: 7 días
                    .sameSite("Strict")
                    .build();

            // Crear la cookie para el nombre de usuario
            ResponseCookie usernameCookie = ResponseCookie.from("username", username)
                    .path("/")
                    .maxAge(cookieTime)
                    .sameSite("Strict")
                    .build();



            // Añadir las cookies a la respuesta
            response.addHeader(HttpHeaders.SET_COOKIE, tokenCookie.toString());
            response.addHeader(HttpHeaders.SET_COOKIE, usernameCookie.toString());


        } catch (Exception e) {
            System.out.println("identifier not found: " + e);
        }

        return ResponseEntity.status(HttpStatus.OK).body(AccessResponseDto.builder().token(token).username(username).build());


        /*
        try {
            User user = userRepository.findByUsername(loginDto.getIdentifier())
                    .orElseGet(() -> personRepository.findByEmail(loginDto.getIdentifier())
                            .map(Person::getUser)
                            .orElseThrow(() -> new UsernameNotFoundException("User not found with identifier: " + loginDto.getIdentifier()))
                    );

            if(!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())){
                throw new Exception("No password match");
            }

            return null;
           // return ResponseEntity.created(location).body("Account created successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating account: " + e.getMessage());
        }
        */


    }

}
