package domain.orders;

import lombok.Value;

@Value
public class ProductId {
    private final String value;

    public static ProductId of(String id) {
        return new ProductId(id);
    }
}
