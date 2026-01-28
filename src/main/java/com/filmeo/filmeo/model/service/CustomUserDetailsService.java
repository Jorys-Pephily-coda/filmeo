package com.filmeo.filmeo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.filmeo.filmeo.model.entity.ConnectedUser;
import com.filmeo.filmeo.model.entity.User;
import com.filmeo.filmeo.model.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired 
    UserRepository userRepository;

    @Override
    // This method is called by Spring Security during login
    // It retrieves the UserDetails (which includes roles/authorities) for authentication
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException( String.format("Ce pseudo (%s) n'existe pas", username) )
        );
        
        // Convert the User entity to ConnectedUser (which implements UserDetails)
        // CRITICAL: ConnectedUser.getAuthorities() MUST return authorities with "ROLE_" prefix
        // For example: ["ROLE_ADMIN", "ROLE_USER"] not ["ADMIN", "USER"]
        // Spring Security automatically adds "ROLE_" when using hasRole() method
        // If your roles don't have the prefix, the access control will fail
        return new ConnectedUser(user);
    }
}
