package com.SeriesAnalyzer.main.Services;

import com.SeriesAnalyzer.main.Dtos.AccountDto;

import com.SeriesAnalyzer.main.Repositories.Login.*;
import com.SeriesAnalyzer.main.Models.Login.*;
import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private IUser userRepository;

    @Autowired
    private IPerson personRepository;

    @Autowired
    private IState stateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserRole userRoleRepository;

    @Autowired
    private IRole roleRepository;

    @Autowired
    private IdentificationTypeService identificationTypeService;



    public Optional<User> getUserFromUsername(String username){
        return userRepository.getUserFromUsername(username);
    }

    public Optional<User> getUserFromToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        String username = authentication.getName();
        return getUserFromUsername(username);
    }

    @Transactional
    public HashMap<String, Object> registerUserAndPerson(AccountDto accountDto, String role) {
        try {
            // Obtener el estado "Active" de la base de datos
            Optional<State> optionalActiveState = stateRepository.findByName("Active");

            if (optionalActiveState.isEmpty()) {
                throw new IllegalStateException("Estado 'Active' no encontrado en la base de datos.");
            }

            State activeState = optionalActiveState.get();

            /*
            Optional<Long> identificationTypeId = identificationTypeService.getIdentificationTypeByName(accountDto.getIdentificationType());

            if(identificationTypeId.isEmpty()){
                throw new NoSuchElementException("Identification Type not found");
            }

             */
            // Crear la entidad Person con el builder
            Person person = Person.builder()
                    .name(accountDto.getName())
                    .phone(accountDto.getPhone())
                    .email(accountDto.getEmail())
                    .birthdate(accountDto.getBirthdate())
                    .identification(accountDto.getIdentification())
                    .identificationType(IdentificationType.builder().id(Long.parseLong(accountDto.getIdentificationTypeId())).build())
                    .build();

            // Obtener Rol por defecto
            Optional<Role> roleOptional = roleRepository.findByRoleName(role);

            if (roleOptional.isEmpty()) {
                throw new IllegalStateException("No se encontró el rol " + role);
            }

            Role userRole = roleOptional.get();

            // Crear la entidad User con el builder y establecer la relación
            User user = User.builder()
                    .username(accountDto.getUsername())
                    .password(passwordEncoder.encode(accountDto.getPassword()))
                    .person(person) // Establecer la relación con Person
                    .userRoles(new HashSet<>()) // Asegúrate de inicializar userRoles
                    .state(activeState) // Establecer el estado por defecto
                    .build();

            // Establecer la relación bidireccional
            person.setUser(user);

            // Crear la relación UserRole
            UserRole userRoleRelation = UserRole.builder()
                    .user(user)
                    .role(userRole)
                    .state(activeState)
                    .build();

            // Agregar la relación UserRole al conjunto de roles del usuario
            user.getUserRoles().add(userRoleRelation);

            // Guardar ambas entidades en la base de datos
            personRepository.save(person);
            userRepository.save(user);
            userRoleRepository.save(userRoleRelation);

            // Preparar el HashMap para retornar
            HashMap<String, Object> result = new HashMap<>();
            result.put("user", user);
            result.put("person", person);

            return result;
        } catch (Exception e) {
            logger.error("Error registering user and person", e);
            throw new RuntimeException("Error registering user and person", e);
        }
    }
}
