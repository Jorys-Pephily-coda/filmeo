package com.filmeo.filmeo.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.filmeo.filmeo.model.entity.User;
import com.filmeo.filmeo.model.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAll() {
        return userRepository.findAll();
    }


    public User getById(int id) {
        Optional<User> User = userRepository.findById(id);
        return User.orElse(new User());
    }

    public User getByUsername(String username) {
        Optional<User> User = userRepository.findByUsername(username);
        return User.orElse(new User());
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));


        String userRole = "PUBLIC";
        user.addRole(userRole);

        userRepository.save(user);
    }
}
