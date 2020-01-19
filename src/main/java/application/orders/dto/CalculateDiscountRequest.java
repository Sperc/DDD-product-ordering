package application.orders.dto;

import lombok.Value;

@Value
public class CalculateDiscountRequest {
    private String code;
    private String purchaserId;
}
