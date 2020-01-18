package domain.orders.dto;

import domain.orders.OrderId;
import lombok.Value;

@Value
public class AddProductResult {
    private final OrderId orderId;
}
