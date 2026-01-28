package com.filmeo.filmeo.model.repository;

import com.filmeo.filmeo.model.entity.StreamingPlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamingPlatformRepository
    extends JpaRepository<StreamingPlatform, Integer> {}
