package application.orders;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This is mock implementation. In production case Product should be getting from Product Domain
 */
@AllArgsConstructor
public class ProductMockSupplier implements ProductSupplier {
    private Map<String, ProductContext> productList;

    @Override
    public Optional<ProductContext> supply(String id) {
        return Optional.ofNullable(productList.get(id));
    }
}
