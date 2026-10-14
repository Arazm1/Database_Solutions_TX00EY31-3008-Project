package fi.metropolia.arazm.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @OneToOne(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("supplier")
    private SupplierAddress supplierAddress;

    public Supplier(){}

    public Supplier(String name, String contactName, String phone, String email){
        this.name = name;
        this.contactName = contactName;
        this.phone = phone;
        this.email = email;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getContactName() {
        return this.contactName;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getEmail() {
        return this.email;
    }

    public SupplierAddress getSupplierAddress(){
        return this.supplierAddress;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }




    
}
