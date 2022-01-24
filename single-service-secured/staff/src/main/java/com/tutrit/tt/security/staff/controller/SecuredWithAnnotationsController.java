package com.tutrit.tt.security.staff.controller;

import com.tutrit.tt.security.staff.bean.Person;
import com.tutrit.tt.security.staff.repository.PersonRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class SecuredWithAnnotationsController {

    private final PersonRepository personRepository;

    public SecuredWithAnnotationsController(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @PostAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/api/person")
    public List<Person> getPersonList() {
        List<Person> persons = personRepository.findAll();
        return persons;
    }
}
