package com.sam.artcollectors_registration.service.implementation;

import com.sam.artcollectors_registration.JPA_Entity.ArtCollector;
import com.sam.artcollectors_registration.dto.ArtCollectorDto;
import com.sam.artcollectors_registration.repository.ArtCollectorRepository;
import com.sam.artcollectors_registration.repository.RoleRepository;
import com.sam.artcollectors_registration.service.ArtCollectorService;
import com.sam.artcollectors_registration.JPA_Entity.Role;
import jakarta.validation.constraints.Email;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtCollectorServiceImpl implements ArtCollectorService {

    private final ArtCollectorRepository artCollectorRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public ArtCollectorServiceImpl(ArtCollectorRepository artCollectorRepository,
                                   RoleRepository roleRepository,
                                   PasswordEncoder passwordEncoder) {
        this.artCollectorRepository = artCollectorRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveArtCollector(ArtCollectorDto artCollectorDto) {
        ArtCollector artCollector = new ArtCollector();
        artCollector.setUserName(artCollectorDto.getUserName());
        artCollector.setEmail(artCollectorDto.getEmail());

        artCollector.setPassword(passwordEncoder.encode(artCollectorDto.getPassword()));


        //Determine the role based on registration criteria
        String roleName;
        if(artCollectorDto.isAdminRegistration()){
            roleName = "ROLE_ADMIN";
        }else{
            roleName= "ROLE_ART_COLLECTOR";
        }

        //Check if role already exists in database, otherwise create it
        Role role = roleRepository.findByName(roleName);
        if(role == null) {
            role = new Role();
            role.setName((roleName));
            roleRepository.save(role);
        }

        //Assign the role to the user
        artCollector.setRoles((Collections.singletonList(role)));
        artCollectorRepository.save(artCollector);
    }




    @Override
    public ArtCollector findArtCollectorByEmail(String email) {
        return artCollectorRepository.findByEmail(email);
    }

    @Override
    public List<ArtCollectorDto> findAllArtCollectors() {
        List<ArtCollector> artCollectors= artCollectorRepository.findAll();
        return artCollectors.stream().map((artCollector) -> convertEntityToDto(artCollector))
                .collect(Collectors.toList());
    }
    private ArtCollectorDto convertEntityToDto(ArtCollector artCollector){
        ArtCollectorDto artCollectorDto = new ArtCollectorDto();

        artCollectorDto.setUserName(artCollector.getUserName());
        artCollectorDto.setEmail(artCollector.getEmail());

        return artCollectorDto;
    }
}
