package application.orders

import domain.orders.Order
import domain.orders.OrderId
import domain.orders.OrderRepository
import domain.orders.PurchaserId

class OrderMockRepository implements OrderRepository {
    Map<String, Order> database = new HashMap<>()


    @Override
    void save(Order order) {
        database.put(order.getId().value, order)
    }

    @Override
    Optional<Order> findByOrderIdAndPurchaserId(OrderId orderId, PurchaserId purchaserId) {
        return Optional.ofNullable(database.get(orderId.value))
    }
}
