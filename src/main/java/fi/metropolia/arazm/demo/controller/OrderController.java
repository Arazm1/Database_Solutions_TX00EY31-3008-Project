package fi.metropolia.arazm.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.metropolia.arazm.demo.dto.CreateOrderRequest;
import fi.metropolia.arazm.demo.dto.OrderDto;
import fi.metropolia.arazm.demo.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) { this.service = service; }

    @GetMapping
    public List<OrderDto> all() { return service.getAllOrders(); }

    @GetMapping("/{id}")
    public OrderDto one(@PathVariable Integer id) { return service.getOrder(id); }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@RequestBody CreateOrderRequest req) {
        return service.createOrder(req);
    }
}