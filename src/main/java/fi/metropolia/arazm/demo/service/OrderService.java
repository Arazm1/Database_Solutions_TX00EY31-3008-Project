package fi.metropolia.arazm.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.metropolia.arazm.demo.dto.CreateOrderRequest;
import fi.metropolia.arazm.demo.dto.OrderDto;
import fi.metropolia.arazm.demo.entity.Customer;
import fi.metropolia.arazm.demo.entity.CustomerAddresses;
import fi.metropolia.arazm.demo.entity.Orders;
import fi.metropolia.arazm.demo.entity.Product;
import fi.metropolia.arazm.demo.mapper.OrderMapper;
import fi.metropolia.arazm.demo.repository.CustomerAddressesRepository;
import fi.metropolia.arazm.demo.repository.CustomerRepository;
import fi.metropolia.arazm.demo.repository.OrdersRepository;
import fi.metropolia.arazm.demo.repository.ProductRepository;

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
    public OrderDto getOrder(Integer id) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
        return mapper.toDto(order);   // mapped while the session is open
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getAllOrders() {
        return ordersRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional
    public OrderDto createOrder(CreateOrderRequest req) {
        Customer customer = customerRepository.findById(req.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        CustomerAddresses address = addressRepository.findById(req.shippingAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        Orders order = new Orders(customer, LocalDateTime.now(), null, address, "NEW");

        for (CreateOrderRequest.Item item : req.items()) {
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.productId()));
            order.addItem(product, item.quantity(), product.getPrice());
        }

        return mapper.toDto(ordersRepository.save(order));
    }
    
}
