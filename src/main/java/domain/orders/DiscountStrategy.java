package domain.orders;

public interface DiscountStrategy {
    Long calculateDiscount(OrderId orderId, Purchaser purchaser, DiscountCode discountCode);

    boolean isApplicable(Purchaser purchaser);
}
