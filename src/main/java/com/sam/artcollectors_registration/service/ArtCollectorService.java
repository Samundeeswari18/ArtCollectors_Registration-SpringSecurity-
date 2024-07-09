package com.sam.artcollectors_registration.service;

import com.sam.artcollectors_registration.JPA_Entity.ArtCollector;
import com.sam.artcollectors_registration.dto.ArtCollectorDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ArtCollectorService {
    void saveArtCollector(ArtCollectorDto artCollectorDto);
    ArtCollector findArtCollectorByEmail(String email);
    List<ArtCollectorDto> findAllArtCollectors();
}
