package application.orders.dto;

import lombok.Value;

@Value
public class CalculateDiscountRequest {
    private String code;
    private String orderId;
    private String purchaserId;
}
