package domain.orders;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WithoutVerificationProductStrategy implements ProductAvailablePolicy {

    @Override
    public boolean isProductAvailable(Product product) {
        //this strategy returns always true
        return true;
    }
}
