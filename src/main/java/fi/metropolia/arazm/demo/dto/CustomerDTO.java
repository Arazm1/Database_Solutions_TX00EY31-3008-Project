package fi.metropolia.arazm.demo.dto;

import java.util.List;

public record CustomerDTO(
    Integer id,
    String firstName,
    String lastName,
    String email,
    String phone,
    List<OrderDto> orders
) {}
