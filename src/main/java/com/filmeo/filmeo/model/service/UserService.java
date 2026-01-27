package com.filmeo.filmeo.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.filmeo.filmeo.model.entity.User;
import com.filmeo.filmeo.model.repository.UserRepository;

public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }


    public User getById(int id) {
        Optional<User> User = userRepository.findById(id);
        return User.orElse(new User());
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }   
    
}
