package domain.orders.dto;

import lombok.Value;

@Value(staticConstructor = "of")
public class CreateOrderResult {
    private final String orderId;
}
