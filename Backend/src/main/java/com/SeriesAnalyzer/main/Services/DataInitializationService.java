package com.SeriesAnalyzer.main.Services;

import com.SeriesAnalyzer.main.Repositories.Login.IRole;
import com.SeriesAnalyzer.main.Repositories.Login.IState;
import com.SeriesAnalyzer.main.Repositories.Login.IStateType;
import com.SeriesAnalyzer.main.Models.Login.Role;
import com.SeriesAnalyzer.main.Models.Login.State;
import com.SeriesAnalyzer.main.Models.Login.StateType;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DataInitializationService {

    @Autowired
    private IRole roleRepository;

    @Autowired
    private IState stateRepository;

    @Autowired
    private IStateType stateTypeRepository;

    // Método para inicializar los datos al iniciar la aplicación
    @PostConstruct
    public void initializeData() {
        // Verificar y agregar roles si no existen
        if (roleRepository.count() == 0) {
            roleRepository.save(Role.builder().id(1L).name("User").roleName("USER").build());
            roleRepository.save(Role.builder().id(2L).name("Admin").roleName("ADMIN").build());
        }

        // Verificar y agregar StateType si no existe
        if (stateTypeRepository.count() == 0) {
            StateType stateType = StateType.builder().name("Basic").id(1L).build();
            stateTypeRepository.save(stateType);

            // Crear estados usando el StateType creado
            State stateInactive = State.builder().name("Inactive").stateType(stateType).build();
            stateRepository.save(stateInactive);
            State stateActive = State.builder().name("Active").stateType(stateType).build();
            stateRepository.save(stateActive);
        } else {
            // Si ya existen StateType, verificar si hay al menos dos estados creados
            long stateCount = stateRepository.count();
            if (stateCount < 2) {
                List<StateType> stateTypes = stateTypeRepository.findAll();
                if (!stateTypes.isEmpty()) {
                    StateType stateType1 = stateTypes.get(0); // Primer StateType existente
                    if (stateRepository.findByNameAndStateType("Active", stateType1).isEmpty()) {
                        State stateActive = State.builder().name("Active").stateType(stateType1).build();
                        stateRepository.save(stateActive);
                    }
                    if (stateRepository.findByNameAndStateType("Inactive", stateType1).isEmpty()) {
                        State stateInactive = State.builder().name("Inactive").stateType(stateType1).build();
                        stateRepository.save(stateInactive);
                    }
                } else {
                    // Manejar caso donde no hay suficientes StateType
                    // Puedes lanzar una excepción o crear más StateType según sea necesario
                    throw new IllegalStateException("No hay suficientes StateType existentes para crear estados.");
                }
            }
        }
    }
}
