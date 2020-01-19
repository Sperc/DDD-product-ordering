package domain.orders;

import domain.shared.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Order {
    private final OrderId id;
    private final ZonedDateTime createdDate;
    private final ProductAvailablePolicy productAvailablePolicy;
    private final OrderNotification orderNotification;
    private final DiscountStrategy discountStrategy;

    private DeliveryInformation deliveryInformation;
    private OrderStatus orderStatus;
    private Long orderPrice;
    private Purchaser purchaser;
    private List<Product> products = new ArrayList<>();

    Order addProduct(Product product) {
        final boolean productAvailable = productAvailablePolicy.isProductAvailable(product);
        if (productAvailable) {
            products.add(product);
            updatePrize(product);
            orderNotification.updateOrderNotification(product, this.getId());
            return this;
        } else {
            throw new ProductException(ErrorStatus.PRODUCT_NOT_AVAILABLE, String.format("Product: %s is not available", product.getProductId()));
        }
    }

    public Order finish() {
        this.orderStatus = OrderStatus.FINISHED;
        orderNotification.sendFinishOrderNotification(this.getId());
        return this;
    }

    public Order addDeliveryInformation(AddDeliveryInformationCommand command) {
        this.deliveryInformation = command.getDeliveryInformation();
        return this;
    }

    public void calculateDiscount(DiscountCode discountCode) {
        final Long priceAfterDiscount = discountStrategy.calculateDiscount(this, discountCode);
        log.info("Price after discount: {}", priceAfterDiscount);
        this.orderPrice = priceAfterDiscount;
    }

    private void updatePrize(Product product) {
        orderPrice += product.getPrice();
    }
}
