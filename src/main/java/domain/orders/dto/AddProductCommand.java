package domain.orders.dto;

import domain.orders.OrderId;
import domain.orders.Product;
import domain.orders.Purchaser;
import lombok.Value;

@Value
public class AddProductCommand {
    private final OrderId orderId;
    private final Product product;
    private final Purchaser purchaser;
}
