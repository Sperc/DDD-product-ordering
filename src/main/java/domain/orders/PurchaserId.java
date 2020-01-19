package domain.orders;

import lombok.Value;

@Value
public class PurchaserId {
    private String value;

    public static PurchaserId of(String s) {
        return new PurchaserId(s);
    }
}
