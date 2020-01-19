package application.orders.dto;

import lombok.Value;

@Value
public class AddProductRequest {
    private final String productId;
    private final String purchaserId;
}
