package domain.orders.dto;

import domain.orders.OrderId;
import lombok.Value;

@Value
public class AddDeliveryAddressResult {
    private final OrderId orderId;
}
