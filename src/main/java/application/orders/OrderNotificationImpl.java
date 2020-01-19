package application.orders;

import domain.orders.OrderId;
import domain.orders.OrderNotification;
import domain.orders.Product;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderNotificationImpl implements OrderNotification {

    @Override
    public void updateOrderNotification(Product product, OrderId orderId) {
        //should send event here
        log.info("Send Update Order Notification, orderId: {}, product id: {}", orderId.getValue(), product.getProductId());

    }

    @Override
    public void sendFinishOrderNotification(OrderId id) {
        //should send event here
        log.info("Send FinishOrder Notification: {}", id.getValue());
    }
}
