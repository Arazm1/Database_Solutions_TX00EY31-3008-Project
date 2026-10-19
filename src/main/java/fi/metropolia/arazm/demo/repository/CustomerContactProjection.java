
package fi.metropolia.arazm.demo.repository;

public interface CustomerContactProjection {

    Integer getCustomerId();

    String getLastName();

    String getFirstName();

    String getEmail();

    String getCountry();
}
