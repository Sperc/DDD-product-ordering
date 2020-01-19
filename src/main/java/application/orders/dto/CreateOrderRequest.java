package application.orders.dto;

import domain.orders.Purchaser;
import domain.orders.dto.CreateOrderCommand;
import lombok.Value;

@Value
public class CreateOrderRequest {
    private final String purchaserId;
    private final String email;
    private final String type;

    public CreateOrderCommand toCommand(Purchaser purchaser) {
        return new CreateOrderCommand(purchaser);
    }
}
