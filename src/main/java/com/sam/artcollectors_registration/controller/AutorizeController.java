package com.sam.artcollectors_registration.controller;

import com.sam.artcollectors_registration.JPA_Entity.ArtCollector;
import com.sam.artcollectors_registration.dto.ArtCollectorDto;
import com.sam.artcollectors_registration.service.ArtCollectorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class AutorizeController {

    private ArtCollectorService artCollectorService;

    @Autowired
    public AutorizeController(ArtCollectorService artCollectorService) {
        this.artCollectorService = artCollectorService;
    }

    @GetMapping("index")
    public String home()
    {
        return "index";
    }

    @GetMapping("/login")
    public String loginForm()
    {
        return "login";
    }

    @GetMapping("/register")
    public String artCollectorRegistration(Model model){
        ArtCollectorDto artCollectorDto=new ArtCollectorDto();
        model.addAttribute("artCollector", artCollectorDto);
        return "art-collector-registration";
    }

    @PostMapping("/register/save")
    public String saveRegistration(@Valid @ModelAttribute("artCollector") ArtCollectorDto artCollector, BindingResult result, Model model) {
        ArtCollector existing = artCollectorService.findArtCollectorByEmail(artCollector.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()) {
            model.addAttribute("artCollector", artCollector);
            return "art-collector-registration";
        }

        artCollectorService.saveArtCollector(artCollector);
        return "redirect:/register?success";
    }


    @GetMapping("/artCollectors")
    public String listRegisteredArtCollector(Model model){
        List<ArtCollectorDto> artCollectors = artCollectorService.findAllArtCollectors();
        model.addAttribute("artCollectors", artCollectors);
        return "registered-art-collectors";

    }

}
