package fi.metropolia.arazm.demo.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import fi.metropolia.arazm.demo.dto.Customer.CustomerPageResponse;
import fi.metropolia.arazm.demo.entity.Customer;
import fi.metropolia.arazm.demo.entity.Orders;
import fi.metropolia.arazm.demo.mapper.CustomerMapper;
import fi.metropolia.arazm.demo.dto.Customer.CustomerAllDTO;
import fi.metropolia.arazm.demo.dto.Customer.CustomerDTO;
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
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with an id: " + id));

        return CustomerMapper.toDto(customer);
    }


    /**
     * Creates a customer and its orders.
     * @param customer the customer data
     * @return the created Customer with its orders.
     */
    public CustomerDTO createCustomer(Customer customer){

        if(customer.getOrders() != null){
            for(Orders order : customer.getOrders()){
                order.setCustomer(customer);
            }
        }

        Customer savedCustomer = customerRepository.save(customer);

        return CustomerMapper.toDto(savedCustomer);
        
    }
    
}
