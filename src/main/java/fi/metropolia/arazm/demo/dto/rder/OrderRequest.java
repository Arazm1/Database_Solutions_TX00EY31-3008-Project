package fi.metropolia.arazm.demo.dto.Order;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderRequest(
                @NotNull @Positive Integer customerId,
                @NotNull @Positive Integer shippingAddressId,
                @NotEmpty @Valid List<Item> items) {
        public record Item(
                        @NotNull @Positive Integer productId,
                        @NotNull @Positive Integer quantity) {
        }
}