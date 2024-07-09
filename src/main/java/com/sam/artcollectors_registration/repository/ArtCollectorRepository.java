package com.sam.artcollectors_registration.repository;

import com.sam.artcollectors_registration.JPA_Entity.ArtCollector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtCollectorRepository extends JpaRepository<ArtCollector, Long> {
    ArtCollector findByEmail(String email);
}
