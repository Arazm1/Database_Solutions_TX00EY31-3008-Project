package fi.metropolia.arazm.demo.dto.customer;

import java.util.List;

import fi.metropolia.arazm.demo.dto.order.OrderDto;

public record CustomerDTO(
    Integer id,
    String firstName,
    String lastName,
    String email,
    String phone,
    List<OrderDto> orders
) {}
