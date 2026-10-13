package fi.metropolia.arazm.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.metropolia.arazm.demo.entity.Orders;
import fi.metropolia.arazm.demo.repository.OrdersRepository;

@RestController 
@RequestMapping("/apiOLD/orders")
public class OrdersController {

    private final OrdersRepository repository;

    public OrdersController(OrdersRepository repository){
        this.repository = repository;
    }

    /**
     * GET /api/orders
     * Fetch all orders
     */
    public List<Orders> getAllOrders(){
        return this.repository.findAll();
    }

    /**
     * GET /api/orders/{id} 
     * Fetch order by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/orders/customer/{customerId}
     * Fetch all orders for a specific customer
     */
    @GetMapping("/customer/{customerId}")
    public List<Orders> getOrdersByCustomerId(@PathVariable Integer customerId) {
        return repository.findByCustomerId(customerId);
    }

    /**
     * POST /api/orders
     * Create a new order
     */
    @PostMapping
    public Orders createOrder(@RequestBody Orders order) {
        return repository.save(order);
    }

    // DELETE /api/orders/{id} - Delete an order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
