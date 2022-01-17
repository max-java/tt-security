package com.tutrit.tt.security.staff.controller;

import com.tutrit.tt.security.staff.bean.Person;
import com.tutrit.tt.security.staff.repository.PersonRepository;
import com.tutrit.tt.security.staff.securityconfig.annotation.AdminRwAllUserRwOnlyFilteredByFullName;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Scanner;

@RestController()
public class AnnotatedController {

    private final PersonRepository personRepository;

    public AnnotatedController(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @PostAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @AdminRwAllUserRwOnlyFilteredByFullName
    @GetMapping("/api/person")
    public List<Person> getPersonList() {
        List<Person> persons = personRepository.findAll();
        return persons;
    }
}
