package com.tutrit.tt.security.staff.controller;

import com.tutrit.tt.security.staff.bean.Person;
import com.tutrit.tt.security.staff.repository.PersonRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PersonController {
    PersonRepository personRepository;

    @GetMapping("/person")
    public ModelAndView getPersons() {
        ModelAndView mov = new ModelAndView();
        mov.addObject("title", "Stuff");
        mov.setViewName("person_list");
        mov.addObject("persons", personRepository.findAll());
        return mov;
    }

    @GetMapping("/person/{id}")
    public ModelAndView getPersonById(@PathVariable Long id) {
        ModelAndView mov = new ModelAndView();
        mov.addObject("title", "Person");
        mov.setViewName("person_card");
        mov.addObject("person", personRepository.findById(id).get());
        return mov;
    }

    @PostMapping("/person/{id}")
    public String savePerson(@ModelAttribute Person person) {
        personRepository.save(person);
        return "redirect:/person";
    }
}
