package com.SeriesAnalyzer.main.Services;

import com.SeriesAnalyzer.main.Repositories.Login.IUser;
import com.SeriesAnalyzer.main.Models.Login.Role;
import com.SeriesAnalyzer.main.Models.Login.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private IUser userRepository;

    public Collection<GrantedAuthority> mapToAuthorities(List<Role> roles){
        return roles.stream().map(role ->
            new SimpleGrantedAuthority(role.getRoleName())
        ).collect(Collectors.toList());
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User Not found"));
        return User.builder().username(user.getUsername()).password(user.getPassword()).userRoles(user.getUserRoles()).state(user.getState()).person(user.getPerson()).build();
    }
}
