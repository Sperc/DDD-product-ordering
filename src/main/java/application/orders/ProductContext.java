package application.orders;

import domain.orders.Product;
import domain.orders.ProductId;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductContext implements Product {
    private final ProductId productId;
    private final Long price;
}
