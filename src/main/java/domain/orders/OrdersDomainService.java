package domain.orders;

import domain.orders.dto.*;
import domain.shared.ErrorStatus;
import domain.shared.OrderException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrdersDomainService {
    private final OrderRepository orderRepository;
    private final OrderFactory orderFactory;

    public CreateOrderResult createOrder(CreateOrderCommand command) {
        final Order order = orderFactory.createOrder(command.getPurchaser());
        orderRepository.save(order);
        return CreateOrderResult.of(order.getId().getValue());
    }

    public AddProductResult addProduct(AddProductCommand command) {
        final Order order = getOrder(command.getOrderId(), command.getPurchaser());
        order.addProduct(command.getProduct());
        return new AddProductResult(order.getId());
    }

    public AddDeliveryAddressResult addDeliveryAddress(AddDeliveryInformationCommand command) {
        final Order order = getOrder(command.getOrderId(), command.getPurchaser());
        order.addDeliveryInformation(command);
        return new AddDeliveryAddressResult(order.getId());
    }

    public void calculateDiscount(CalculateDiscountCommand command) {
        final Order order = getOrder(command.getOrderId(), command.getPurchaser());
        order.calculateDiscount(command.getDiscountCode());
    }

    public void finish(OrderId orderId, Purchaser purchaser) {
        final Order order = getOrder(orderId, purchaser);
        order.finish();
    }

    private Order getOrder(OrderId orderId, Purchaser purchaser) {
        return orderRepository.findByOrderIdAndPurchaserId(orderId, purchaser.getPurchaserId())
                              .orElseThrow(() -> new OrderException(ErrorStatus.ORDER_IS_NOT_EXISTS,
                                                                    String.format("Order: %s doesn't exists",
                                                                                  orderId.getValue())));
    }
}
