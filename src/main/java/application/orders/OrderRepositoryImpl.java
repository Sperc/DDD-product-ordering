package application.orders;

import domain.orders.Order;
import domain.orders.OrderId;
import domain.orders.OrderRepository;
import domain.orders.PurchaserId;

import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {
    @Override
    public void save(Order order) {

    }

    @Override
    public Optional<Order> findByOrderIdAndPurchaserId(OrderId orderId, PurchaserId purchaserId) {
        return Optional.empty();
    }
}
