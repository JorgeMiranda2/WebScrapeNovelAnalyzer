package com.SeriesAnalyzer.main.Controllers.Login;

import com.SeriesAnalyzer.main.Models.Login.Person;
import com.SeriesAnalyzer.main.Services.PersonService;
import com.SeriesAnalyzer.main.Utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/person")
    public ResponseEntity<String> createPerson(@RequestBody Person person) {
        Long savedPersonId = personService.save(person);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        URI location = URI.create("/api/person/" + savedPersonId);
        return ResponseEntity.created(location).headers(headers).body("{\"message\": \"Person created successfully\"}");
    }

    @GetMapping("/person")
    public ResponseEntity<List<Person>> listPersons() {
        List<Person> persons = personService.list();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        return ResponseEntity.ok().headers(headers).body(persons);
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Optional<Person> personOptional = personService.getPersonById(id);

        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            return ResponseEntity.ok().body(person);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/person/{id}")
    public ResponseEntity<String> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        Optional<Person> existingPersonOptional = personService.getPersonById(id);

        if (existingPersonOptional.isPresent()) {
            Person existingPerson = existingPersonOptional.get();
            existingPerson.setName(person.getName());
            existingPerson.setEmail(person.getEmail());
            existingPerson.setPhone(person.getPhone());
            existingPerson.setBirthdate(person.getBirthdate());
            existingPerson.setIdentification(person.getIdentification());
            existingPerson.setIdentificationType(person.getIdentificationType());

            personService.save(existingPerson);

            return ResponseUtils.CreateSuccessResponse("Person updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable Long id) {
        Optional<Person> personOptional = personService.getPersonById(id);

        if (personOptional.isPresent()) {
            personService.delete(personOptional.get());
            return ResponseEntity.ok().body("{\"message\": \"Person deleted successfully\"}");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
