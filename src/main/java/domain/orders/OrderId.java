package domain.orders;

import lombok.Value;

@Value
public class OrderId {
    private final String value;

    public static OrderId of(String id) {
        return new OrderId(id);
    }
}
