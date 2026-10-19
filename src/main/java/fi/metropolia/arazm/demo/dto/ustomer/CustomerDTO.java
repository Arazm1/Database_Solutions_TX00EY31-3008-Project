package fi.metropolia.arazm.demo.dto.ustomer;

import java.util.List;

import fi.metropolia.arazm.demo.dto.order.OrderDTO;

public record CustomerDTO(
    Integer id,
    String firstName,
    String lastName,
    String email,
    String phone,
    List<OrderDTO> orders
) {}
