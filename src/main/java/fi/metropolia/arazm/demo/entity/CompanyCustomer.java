package fi.metropolia.arazm.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

//@Entity
@DiscriminatorValue("COMPANY")
public class CompanyCustomer extends Customer {

    @Column(name = "company_name")
    private String companyName;

    public CompanyCustomer() {
        super();
    }

    public CompanyCustomer(String firstName, String lastName, String email, String phone, String companyName) {
        super(firstName, lastName, email, phone);
        this.companyName = companyName;
    }


    public String getCompanyName() {
        return this.companyName;
    }

    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}