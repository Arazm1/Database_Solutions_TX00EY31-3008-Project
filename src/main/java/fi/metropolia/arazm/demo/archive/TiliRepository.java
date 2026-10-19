package fi.metropolia.arazm.demo.archive;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.metropolia.arazm.demo.entity.Person;

public interface TiliRepository extends JpaRepository<Person, Integer>{
    
}
