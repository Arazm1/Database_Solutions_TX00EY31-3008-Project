package fi.metropolia.arazm.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.metropolia.arazm.demo.dto.order.OrderDTO;
import fi.metropolia.arazm.demo.dto.order.OrderPageDTO;
import fi.metropolia.arazm.demo.dto.order.OrderRequest;
import fi.metropolia.arazm.demo.dto.order.StatusUpdateRequest;
import fi.metropolia.arazm.demo.service.OrderService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    /**
     * Retrieves all orders.
     */
    @GetMapping
    public List<OrderDTO> getAllOrders(){
        return service.getAllOrders();
    }

    /**
     * Retrieves a paginated list of orders.
     */
    @GetMapping("/paged")
    public OrderPageDTO getPagedOrders(
           @RequestParam(defaultValue = "0") @Min(0) int page,
           @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {

        return service.getPagedOrders(page, size);
    }

    /**
     * Retrieves a single order by ID.
     */
    @GetMapping("/{id}")
    public OrderDTO one(@PathVariable @Positive Integer id) {
        return service.getOrder(id);
    }

    /**
     * Creates a new order.
     */
    @PostMapping
    public OrderDTO create(@RequestBody OrderRequest req){
        return service.createOrder(req);
    }

    /**
     * Updates the status of an order by ID.
     */
    @PutMapping("/{id}/status")
    public OrderDTO updateStatus(
            @PathVariable @Positive Integer id,
            @RequestBody StatusUpdateRequest request) {
        return service.updateOrderStatus(id, request.status());
    }

    /**
     * Retrieves all orders belonging to a specific customer.
     */
    @GetMapping("/customer/{id}")
    public List<OrderDTO> getOrdersByCustomer(
            @PathVariable @Positive Integer id) {
        return service.getOrdersByCustomer(id);
    }

    /**
     * Deletes an order by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable @Positive Integer id) {
        service.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}