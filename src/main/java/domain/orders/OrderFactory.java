package domain.orders;

import domain.shared.UuidGenerator;
import lombok.RequiredArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.ArrayList;

@RequiredArgsConstructor
public class OrderFactory {
    private final Clock clock;
    private final UuidGenerator uuidGenerator;
    private final OrderNotification orderNotification;
    private final DiscountStrategyFactory discountStrategyFactory;

    public Order createOrder(Purchaser purchaser) {
        final DiscountStrategy strategy = discountStrategyFactory.getStrategy(purchaser);
        final OrderId id = OrderId.of(uuidGenerator.generateUuid());
        return new Order(id,
                         ZonedDateTime.now(clock),
                         new WithoutVerificationProductStrategy(),
                         orderNotification,
                         strategy,
                         DeliveryInformation.createEmpty(),
                         OrderStatus.IN_PROGRESS,
                         0L,
                         purchaser,
                         new ArrayList<>()
        );

    }
}
