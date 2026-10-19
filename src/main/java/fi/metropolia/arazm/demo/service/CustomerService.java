package fi.metropolia.arazm.demo.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import fi.metropolia.arazm.demo.dto.customer.CustomerAllDTO;
import fi.metropolia.arazm.demo.dto.customer.CustomerContactDTO;
import fi.metropolia.arazm.demo.dto.customer.CustomerDTO;
import fi.metropolia.arazm.demo.dto.customer.CustomerPageResponse;
import fi.metropolia.arazm.demo.dto.customer.CustomerRequest;
import fi.metropolia.arazm.demo.entity.Customer;
import fi.metropolia.arazm.demo.mapper.CustomerMapper;
import fi.metropolia.arazm.demo.repository.CustomerRepository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service 
public class CustomerService {


    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    /**
     * Converts a Customer entity to the DTO used in customer lists.
     */
    private CustomerAllDTO toCustomerAllDTO(Customer customer) {
        return new CustomerAllDTO(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone()
        );
    }


    /**
     * GET /api/customer
     * @return all customers
     */
    @Transactional(readOnly = true)
    public List<CustomerAllDTO> getAllCustomers(){
        return customerRepository.findAll()
                        .stream()
                        .map(this::toCustomerAllDTO)
                        .toList();
        }


    @Transactional(readOnly = true)
    public CustomerPageResponse getPagedCustomers(int page, int size){

        Pageable pageable = PageRequest.of(page, size);

        Page<Customer> customerPage = customerRepository.findAll(pageable);

        List<CustomerAllDTO> customers = customerPage.getContent()
                                                        .stream()
                                                        .map(this::toCustomerAllDTO)
                                                        .toList();

        return new CustomerPageResponse(
            customers,
            customerPage.getNumber(),
            customerPage.getSize(),
            customerPage.getTotalElements(),
            customerPage.getTotalPages()
        );
    }



    /**
     * Retrieves specific customer with their orders.
     * 
     * @param id the customer ID
     * @return the customer DTO
     */
    @Transactional(readOnly = true)
    public CustomerDTO getCustomerById(Integer id){

        Customer customer = customerRepository.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with an ID " + id));

        return CustomerMapper.toDto(customer);
    }


    @Transactional
    public CustomerDTO createCustomer(CustomerRequest req) {
        Customer customer = new Customer();
        apply(customer, req);
        return CustomerMapper.toDto(customerRepository.save(customer));
    }

    @Transactional
    public CustomerDTO updateCustomer(Integer id, CustomerRequest req) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Customer not found with an ID " + id));
        apply(customer, req);
        return CustomerMapper.toDto(customer);
    }


    @Transactional
    public void deleteCustomer(Integer id){
        if(!customerRepository.existsById(id)){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Customer not found with an ID " + id
            );
        }
        customerRepository.deleteById(id);
    }


    private void apply(Customer c, CustomerRequest req) {
        c.setFirstName(req.firstName());
        c.setLastName(req.lastName());
        c.setEmail(req.email());
        c.setPhone(req.phone());
    }



    /**
     * Retrives customer contacts from View nakyma2
     */
    public List<CustomerContactDTO> getCustomerContacts(){
        return customerRepository.findCustomerContacts()
                                    .stream()
                                    .map(contact -> new CustomerContactDTO(
                                        contact.getCustomerId(),
                                        contact.getLastName(),
                                        contact.getFirstName(),
                                        contact.getEmail(),
                                        contact.getCountry()
                                    )).toList();
    }
    
}
