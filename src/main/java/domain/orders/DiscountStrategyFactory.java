package domain.orders;

import domain.shared.OrderException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DiscountStrategyFactory {
    private final List<DiscountStrategy> strategyList;

    DiscountStrategy getStrategy(Purchaser purchaser) {
        return strategyList.stream()
                           .filter(discountStrategy -> discountStrategy.isApplicable(purchaser))
                           .findFirst()
                           .orElseThrow(() -> new OrderException(ErrorStatus.INVALID_CONFIGURATION, "Invalid configuration"));
    }
}
