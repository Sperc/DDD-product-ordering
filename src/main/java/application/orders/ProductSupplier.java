package application.orders;

import java.util.Optional;

public interface ProductSupplier {
    Optional<ProductContext> supply(String id);
}
