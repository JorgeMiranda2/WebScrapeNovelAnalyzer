package com.SeriesAnalyzer.main.Security;

import com.SeriesAnalyzer.main.Services.AuthService;
import com.SeriesAnalyzer.main.Services.RoleService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private RoleService roleService;

    private String obatinTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.out.println("Entrando al filter");

        String token = this.obatinTokenFromHeader(request); //agregado el this

        System.out.println("token: " + token);

        if(StringUtils.hasText(token)&& jwtAuthenticationProvider.validateToken(token)){
            String username = jwtAuthenticationProvider.obtainUsernameFromToken(token);
            UserDetails userDetails = authService.loadUserByUsername(username);
            List<String> userRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            System.out.println("Autoridades obtenidas: " + userRoles);
            List<String> allRoles = roleService.getAllRoleNames();
            System.out.println("Autoridades posibles: " + allRoles);
            if (userRoles.stream().anyMatch(allRoles::contains)) {
                System.out.println("Contiene la autoridad");
                // Configurar la autenticación en el contexto de seguridad
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
        }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Aquí especificamos las rutas que no deben ser filtradas
        String path = request.getRequestURI();
        return path.startsWith("/auth/");
    }

    }

