package domain.orders;

import java.util.Optional;

public interface OrderRepository {
    void save(Order order);

    Optional<Order> findByOrderIdAndPurchaserId(OrderId orderId, PurchaserId purchaserId);
}
