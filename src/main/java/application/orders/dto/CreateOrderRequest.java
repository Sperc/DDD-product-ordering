package application.orders.dto;

import domain.orders.Purchaser;
import domain.orders.dto.CreateOrderCommand;
import lombok.Value;

@Value
public class CreateOrderRequest {
    private final String purchaserId;

    public CreateOrderCommand toCommand(Purchaser purchaser) {
        return new CreateOrderCommand(purchaser);
    }
}
