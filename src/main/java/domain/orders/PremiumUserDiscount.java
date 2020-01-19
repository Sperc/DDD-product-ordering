package domain.orders;

public class PremiumUserDiscount implements DiscountStrategy {
    @Override
    public Long calculateDiscount(Order order, DiscountCode discountCode) {
        //here can handle discount code
        return (long) (order.getOrderPrice() * 0.8);
    }

    @Override
    public boolean isApplicable(Purchaser purchaser) {
        return Purchaser.Type.PREMIUM == purchaser.getType();
    }
}
