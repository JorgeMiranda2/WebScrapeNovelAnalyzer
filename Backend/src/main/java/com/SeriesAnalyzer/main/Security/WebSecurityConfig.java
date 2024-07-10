package com.SeriesAnalyzer.main.Security;

import com.SeriesAnalyzer.main.Models.Login.Permission;
import com.SeriesAnalyzer.main.Services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private PermissionService permissionService;

    private static final String[] ALLOWED_ORIGINS = {
            "http://localhost:8080",
            "http://localhost:4200",
            "http://172.16.77.34:3000",
            "http://localhost:5173",
            "http://localhost:19006",
            "http://localhost",
            "http://172.16.77.34:19006",
            "http://172.16.77.34",
            "http://localhost:3000",
            "http://loginfront:3000",
            "http://loginfront:80",
    };

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    @Transactional(readOnly = true)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/auth/**").permitAll();

                    List<Permission> permissions = permissionService.getAllPermissions();

                    Map<String, List<String>> routeRolesMap = new HashMap<>();

                    // Agrupar los roles por ruta y método
                    for (Permission permission : permissions) {
                        String routeMethodKey = permission.getMethod() + ":" + permission.getRoute();
                        routeRolesMap.computeIfAbsent(routeMethodKey, k -> new ArrayList<>())
                                .add(permission.getRole().getRoleName());
                    }

                    // Configurar la autorización para cada ruta y método
                    for (Map.Entry<String, List<String>> entry : routeRolesMap.entrySet()) {
                        String[] routeMethod = entry.getKey().split(":");
                        String method = routeMethod[0];
                        String route = routeMethod[1];

                        List<String> roles = entry.getValue();

                        authorize.requestMatchers(new AntPathRequestMatcher(route, method))
                                .hasAnyAuthority(roles.toArray(new String[0]));
                    }

                    authorize.anyRequest().authenticated();
                })
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedHeaders("*", "Authorization")
                        .allowedOrigins(ALLOWED_ORIGINS) // Explicitly list allowed origins
                        .allowedMethods("*")
                        .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}
