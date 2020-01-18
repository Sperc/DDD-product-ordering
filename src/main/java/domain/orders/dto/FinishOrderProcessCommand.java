package domain.orders.dto;

import domain.orders.OrderId;
import domain.orders.Purchaser;
import lombok.Value;

@Value
public class FinishOrderProcessCommand {
    private final OrderId orderId;
    private final Purchaser purchaser;
}
