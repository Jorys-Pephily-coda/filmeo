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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException( String.format("Ce pseudo (%s) n'existe pas", username) )
        );
        return new ConnectedUser(user);
    }
}
