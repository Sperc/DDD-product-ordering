package domain.orders;

public interface OrderNotification {
    void updateOrderNotification(Product product, OrderId orderId);

    void sendFinishOrderNotification(OrderId id);
}
