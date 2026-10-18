package fi.metropolia.arazm.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.metropolia.arazm.demo.dto.Order.CreateOrderRequest;
import fi.metropolia.arazm.demo.dto.Order.OrderDto;
import fi.metropolia.arazm.demo.dto.Order.OrderPageDTO;
import fi.metropolia.arazm.demo.service.OrderService;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<OrderDto> all(){
        return service.getAllOrders();
    }

    @GetMapping("/paged")
    public OrderPageDTO getPagedOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        return service.getPagedOrders(page, size);
    }

    @GetMapping("/{id}")
    public OrderDto one(@PathVariable Integer id) {
        return service.getOrder(id);
    }

    @PostMapping
    public OrderDto create(@RequestBody CreateOrderRequest req){
        return service.createOrder(req);
    }
}