package application.orders;

import domain.orders.*;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private Map<String, Order> database = new HashMap<>();
    @Override
    public void save(Order order) {
        database.put(order.getId().getValue(), order);
    }

    @Override
    public Optional<Order> findByOrderIdAndPurchaserId(OrderId orderId, PurchaserId purchaserId) {
        return Optional.ofNullable(database.get(orderId.getValue()));
    }
}
