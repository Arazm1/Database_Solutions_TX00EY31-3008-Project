package fi.metropolia.arazm.demo.dto.order;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderRequest(
                @NotNull @Positive Integer customerId,
                @NotNull @Positive Integer shippingAddressId,
                @NotEmpty List<@Valid Item> items) {
        public record Item(
                        @NotNull @Positive Integer productId,
                        @NotNull @Positive Integer quantity) {
        }
}