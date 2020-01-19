package domain.orders;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrdinaryUserDiscount implements DiscountStrategy {
    @Override
    public Long calculateDiscount(Order order, DiscountCode discountCode) {
        log.info("Ordinary discount");
        return order.getOrderPrice();
    }

    @Override
    public boolean isApplicable(Purchaser purchaser) {
        return Purchaser.Type.ORDINARY == purchaser.getType();
    }
}
