package com.filmeo.filmeo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filmeo.filmeo.model.entity.StreamingPlatform;

@Repository
public interface StreamingPlatformRepository extends JpaRepository<StreamingPlatform, Integer> {}