package com.filmeo.filmeo.model.service;

import com.filmeo.filmeo.model.entity.StreamingPlatform;
import com.filmeo.filmeo.model.repository.StreamingPlatformRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

public class StreamingPlatformService {

    @Autowired
    private StreamingPlatformRepository streamingPlatformRepository;

    public List<StreamingPlatform> getAll() {
        return streamingPlatformRepository.findAll();
    }

    public StreamingPlatform getById(int id) {
        Optional<StreamingPlatform> StreamingPlatform =
            streamingPlatformRepository.findById(id);
        return StreamingPlatform.orElse(new StreamingPlatform());
    }

    public StreamingPlatform update(StreamingPlatform streamingPlatform) {
        return streamingPlatformRepository.save(streamingPlatform);
    }

    public void delete(StreamingPlatform streamingPlatform) {
        streamingPlatformRepository.delete(streamingPlatform);
    }
}
