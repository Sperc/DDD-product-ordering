package domain.orders;

import lombok.Value;

@Value
public class AddDeliveryInformationCommand {
    private final OrderId orderId;
    private final DeliveryInformation deliveryInformation;
    private final Purchaser purchaser;
}
