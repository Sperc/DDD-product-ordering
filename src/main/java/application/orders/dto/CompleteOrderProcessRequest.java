package application.orders.dto;

import lombok.Value;

@Value
public class CompleteOrderProcessRequest {
    private final String purchaserId;
    private final String orderId;
}
