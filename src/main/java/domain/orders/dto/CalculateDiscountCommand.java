package domain.orders.dto;

import domain.orders.DiscountCode;
import domain.orders.OrderId;
import domain.orders.Purchaser;
import lombok.Value;

@Value
public class CalculateDiscountCommand {
    private final DiscountCode discountCode;
    private final Purchaser purchaser;
    private final OrderId orderId;
}
