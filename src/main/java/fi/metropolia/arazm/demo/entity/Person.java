package fi.metropolia.arazm.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "person")
public class Person {
    @Id 
    private Integer id;

    private String firstname;
    private String lastname;

    public Person(){

    }

    public String getFirstname(){
        return this.firstname;
    }

    public String getLastname(){
        return this.lastname;
    }
    
}
