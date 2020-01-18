package domain.orders.dto;

import domain.orders.Purchaser;
import lombok.Value;

@Value
public class CreateOrderCommand {
    private final Purchaser purchaser;
}
