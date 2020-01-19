package domain.orders;

public interface DiscountStrategy {
    Long calculateDiscount(Order order, DiscountCode discountCode);

    boolean isApplicable(Purchaser purchaser);
}
