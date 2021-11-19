package com.tutrit.tt.security.staff.controller;

import com.tutrit.tt.security.staff.repository.PersonRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PersonRestController {
    PersonRepository personRepository;

    @PatchMapping(value = "/api/card/{id}", consumes = MediaType.TEXT_PLAIN_VALUE)
    public void updateStatusByCardId(@PathVariable Long id, @RequestBody String status) {
        personRepository.updateStatus(id, Boolean.valueOf(status));
    }
}
