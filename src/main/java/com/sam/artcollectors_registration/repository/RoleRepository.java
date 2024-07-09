package com.sam.artcollectors_registration.repository;

import com.sam.artcollectors_registration.JPA_Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
