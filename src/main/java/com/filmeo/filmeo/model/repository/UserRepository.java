package com.filmeo.filmeo.model.repository;

import com.filmeo.filmeo.model.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByUsername(String username);
}
