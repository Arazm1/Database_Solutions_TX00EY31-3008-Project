package fi.metropolia.arazm.demo.service;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import fi.metropolia.arazm.demo.common.enums.OrderStatus;
import fi.metropolia.arazm.demo.dto.order.OrderDTO;
import fi.metropolia.arazm.demo.dto.order.OrderPageDTO;
import fi.metropolia.arazm.demo.dto.order.OrderRequest;
import fi.metropolia.arazm.demo.entity.Customer;
import fi.metropolia.arazm.demo.entity.CustomerAddresses;
import fi.metropolia.arazm.demo.entity.Orders;
import fi.metropolia.arazm.demo.entity.Product;
import fi.metropolia.arazm.demo.mapper.OrderMapper;
import fi.metropolia.arazm.demo.repository.CustomerAddressesRepository;
import fi.metropolia.arazm.demo.repository.CustomerRepository;
import fi.metropolia.arazm.demo.repository.OrdersRepository;
import fi.metropolia.arazm.demo.repository.ProductRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

@Service
public class OrderService {

    private final OrdersRepository ordersRepository;
    private final CustomerRepository customerRepository;
    private final CustomerAddressesRepository addressRepository;
    private final ProductRepository productRepository;
    private final OrderMapper mapper;


    public OrderService(OrdersRepository ordersRepository,
                        CustomerRepository customerRepository,
                        CustomerAddressesRepository addressRepository,
                        ProductRepository productRepository,
                        OrderMapper mapper) {
        this.ordersRepository = ordersRepository;
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }


    @Transactional(readOnly = true)
    public OrderDTO getOrder(Integer id) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with an ID " + id));
        return mapper.toDto(order);
    }


    @Transactional(readOnly = true)
    public OrderPageDTO getPagedOrders(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        Page<Orders> orderPage = ordersRepository.findAll(pageable);

        List<OrderDTO> orders = orderPage.getContent()
                        .stream()
                        .map(mapper::toDto)
                        .toList();


        return new OrderPageDTO(
            orders,
            orderPage.getNumber(),
            orderPage.getSize(),
            orderPage.getTotalElements(),
            orderPage.getTotalPages()
        );
    }


    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        return ordersRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional
    public OrderDTO createOrder(OrderRequest req) {
        Customer customer = customerRepository.findById(req.customerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found " + req.customerId()));
        CustomerAddresses address = addressRepository.findById(req.shippingAddressId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found " + req.shippingAddressId()));

        Orders order = new Orders(customer, LocalDateTime.now(), null, address, OrderStatus.NEW);

        for (OrderRequest.Item item : req.items()) {
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + item.productId()));
            order.addItem(product, item.quantity(), product.getPrice());
        }

        return mapper.toDto(ordersRepository.save(order));
    }

    public List<OrderDTO> getOrdersByCustomer(Integer customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Customer not found: " + customerId
            );
        }

        return ordersRepository.findByCustomerId(customerId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public void deleteOrder(Integer id) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Order not found with ID " + id
                ));

        ordersRepository.delete(order);
    }



    @Transactional
    public OrderDTO updateOrderStatus(Integer id, OrderStatus status){
        Orders order = ordersRepository.findById(id)
                                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with an ID " + id));

        order.setStatus(status);
        return mapper.toDto(order);
    }
    
}
