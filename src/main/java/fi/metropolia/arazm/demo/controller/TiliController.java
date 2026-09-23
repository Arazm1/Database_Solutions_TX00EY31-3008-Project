package fi.metropolia.arazm.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.metropolia.arazm.demo.entity.Person;
import fi.metropolia.arazm.demo.repository.TiliRepository;

@RestController 
@RequestMapping("/tili")
public class TiliController {

    private final TiliRepository repository;

    public TiliController(TiliRepository repository){
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getTiliById(@PathVariable Integer id){
        return repository.findById(id)
            .map(tili -> ResponseEntity.ok(tili))
            .orElse(ResponseEntity.notFound().build());
    }
    
}
