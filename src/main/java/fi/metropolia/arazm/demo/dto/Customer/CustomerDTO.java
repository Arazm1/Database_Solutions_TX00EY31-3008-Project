package fi.metropolia.arazm.demo.dto.Customer;

import java.util.List;

import fi.metropolia.arazm.demo.dto.OrderDto;

public record CustomerDTO(
    Integer id,
    String firstName,
    String lastName,
    String email,
    String phone,
    List<OrderDto> orders
) {}
