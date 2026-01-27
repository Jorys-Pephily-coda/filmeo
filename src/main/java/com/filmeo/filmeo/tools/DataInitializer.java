package com.filmeo.filmeo.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.filmeo.filmeo.model.entity.User;
import com.filmeo.filmeo.model.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create default admin user if it doesn't exist
        if (userRepository.findByUsername("admin").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@filmeo.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.addRole("ADMIN");
            adminUser.addRole("USER");
            userRepository.save(adminUser);
            System.out.println("✓ Admin user created: username=admin, password=admin123");
        }

        // Create default public user if it doesn't exist
        if (userRepository.findByUsername("user").isEmpty()) {
            User publicUser = new User();
            publicUser.setUsername("user");
            publicUser.setEmail("user@filmeo.com");
            publicUser.setPassword(passwordEncoder.encode("user123"));
            publicUser.addRole("USER");
            userRepository.save(publicUser);
            System.out.println("✓ Test user created: username=user, password=user123");
        }
    }
}
